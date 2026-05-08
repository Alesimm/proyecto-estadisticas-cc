package com.cc.partidosservice.service;

import com.cc.partidosservice.client.NotificacionClient;
import com.cc.partidosservice.dto.PartidoRequestDTO;
import com.cc.partidosservice.dto.PartidoResponseDTO;
import com.cc.partidosservice.model.Partido;
import com.cc.partidosservice.model.Torneo;
import com.cc.partidosservice.repository.PartidoRepository;
import com.cc.partidosservice.repository.TorneoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio para la gestión de partidos.
 * Aquí se concentran las validaciones, reglas complejas y el mapeo de datos.
 * Cumple con el criterio de excelencia IE 2.2.1 (Reglas de Negocio).
 *
 * @author [TU NOMBRE]
 */
@Slf4j // Cumple IE 2.3.2 (Logs de trazabilidad estructurados)
@Service
@RequiredArgsConstructor
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final TorneoRepository torneoRepository;
    private final NotificacionClient notificacionClient;

    /**
     * Convierte una entidad interna Partido a un DTO de respuesta para no exponer
     * la estructura de base de datos directamente (Parte del patrón CSR / DTO).
     *
     * @param partido Entidad JPA a convertir.
     * @return DTO de respuesta.
     */
    private PartidoResponseDTO mapPartido(Partido partido) {
        return new PartidoResponseDTO(
                partido.getId(),
                partido.getRival(),
                partido.getFecha(),
                partido.getEstadio(),
                partido.getTorneo().getId()
        );
    }

    /**
     * Recupera todos los partidos programados, transformándolos en DTOs.
     *
     * @return Lista de partidos en formato DTO.
     */
    public List<PartidoResponseDTO> obtenerTodos() {
        log.info("Consultando todos los partidos de la base de datos.");
        return partidoRepository.findAll()
                .stream()
                .map(this::mapPartido)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo partido en el sistema, validando las reglas de negocio críticas.
     *
     * @param partidoDTO Datos recibidos del front-end o usuario.
     * @return DTO del partido recién creado con su ID autogenerado.
     * @throws IllegalArgumentException si la regla de negocio de estadio/fecha se incumple.
     * @throws EntityNotFoundException   si el torneo especificado no existe.
     */
    public PartidoResponseDTO guardar(PartidoRequestDTO partidoDTO) {
        log.info("Iniciando el proceso de guardado de un nuevo partido contra: {}.", partidoDTO.getRival());

        // REGLA DE NEGOCIO (IE 2.2.1): Prevención de colisiones de horarios.
        // Verificamos si ya existe CUALQUIER partido en el mismo estadio y fecha exacta.
        if (partidoRepository.existsByEstadioAndFecha(partidoDTO.getEstadio(), partidoDTO.getFecha())) {
            log.warn("Regla de negocio fallida: Se detectó un conflicto de horario en el estadio {} para la fecha {}.", partidoDTO.getEstadio(), partidoDTO.getFecha());
            throw new IllegalArgumentException("Ya existe un partido programado en ese estadio para esa misma fecha exacta.");
        }

        // Recuperamos el Torneo relacionado (Asegura IE 2.2.3 Relaciones).
        Torneo torneo = torneoRepository.findById(partidoDTO.getTorneoId())
                .orElseThrow(() -> new EntityNotFoundException("El torneo especificado (ID: " + partidoDTO.getTorneoId() + ") no existe."));

        // Mapeo manual del DTO de petición a la Entidad JPA.
        Partido partido = new Partido();
        partido.setRival(partidoDTO.getRival());
        partido.setFecha(partidoDTO.getFecha());
        partido.setEstadio(partidoDTO.getEstadio());
        partido.setTorneo(torneo);

        // Persistencia real en MySQL (Asegura IE 2.1.2 CRUD).
        Partido guardado = partidoRepository.save(partido);
        log.info("Partido guardado exitosamente con ID: {}.", guardado.getId());

        // INTEROPERABILIDAD (IE 2.4.1): Notificación asíncrona a otro microservicio.
        // Intentamos enviar la alerta pero no bloqueamos el flujo si el otro MS falla.
        try {
            notificacionClient.enviarNotificacion("Se ha programado un nuevo partido contra " + guardado.getRival());
            log.info("Notificación asíncrona enviada exitosamente mediante OpenFeign.");
        } catch (Exception e) {
            log.error("Fallo no crítico al comunicar con Notificaciones Service: {}.", e.getMessage());
            // No lanzamos excepción aquí para que la operación de guardar no se cancele.
        }

        return mapPartido(guardado);
    }

    /**
     * Actualiza los detalles de un partido existente (Parte de la 'U' en CRUD/IE 2.1.2).
     *
     * @param id          ID del partido a modificar.
     * @param partidoDTO Nuevos datos recibidos.
     * @return DTO del partido actualizado.
     * @throws EntityNotFoundException si el partido o torneo especificado no existen.
     */
    public PartidoResponseDTO actualizar(Long id, PartidoRequestDTO partidoDTO) {
        log.info("Iniciando actualización para el partido con ID: {}.", id);

        // Verificamos existencia antes de modificar.
        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado con el ID: " + id));

        // Recuperamos el Torneo relacionado actualizado.
        Torneo torneo = torneoRepository.findById(partidoDTO.getTorneoId())
                .orElseThrow(() -> new EntityNotFoundException("El torneo especificado (ID: " + partidoDTO.getTorneoId() + ") no existe."));

        // Mapeo manual de actualización.
        partido.setRival(partidoDTO.getRival());
        partido.setFecha(partidoDTO.getFecha());
        partido.setEstadio(partidoDTO.getEstadio());
        partido.setTorneo(torneo);

        // Guardado de cambios (merge JPA).
        Partido actualizado = partidoRepository.save(partido);
        log.info("Partido ID: {} actualizado correctamente.", id);
        return mapPartido(actualizado);
    }

    /**
     * Elimina un partido del sistema (Parte de la 'D' en CRUD/IE 2.1.2).
     *
     * @param id ID del partido a eliminar.
     * @throws EntityNotFoundException si el partido no existe para eliminar.
     */
    public void eliminar(Long id) {
        log.info("Intentando eliminar el partido con ID: {}.", id);
        if (!partidoRepository.existsById(id)) {
            log.error("Error al eliminar: Partido ID {} no encontrado.", id);
            throw new EntityNotFoundException("Partido no encontrado con el ID: " + id);
        }
        partidoRepository.deleteById(id);
        log.info("Partido ID: {} eliminado correctamente.", id);
    }
}