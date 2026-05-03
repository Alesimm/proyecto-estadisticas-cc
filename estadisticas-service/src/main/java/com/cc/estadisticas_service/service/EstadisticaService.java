package com.cc.estadisticas_service.service;

import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.model.Estadistica;
import com.cc.estadisticas_service.repository.EstadisticaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadisticaService {

    private final EstadisticaRepository repository;

    public EstadisticaResponseDTO guardar(EstadisticaRequestDTO requestDTO) {
        log.info("Iniciando guardado de estadísticas para el jugador ID: {}", requestDTO.getJugadorId());

        validarLogicaDeEstadisticas(requestDTO);

        Estadistica entidad = Estadistica.builder()
                .jugadorId(requestDTO.getJugadorId())
                .partidoId(requestDTO.getPartidoId())
                .goles(requestDTO.getGoles())
                .asistencias(requestDTO.getAsistencias())
                .intercepciones(requestDTO.getIntercepciones())
                .recuperaciones(requestDTO.getRecuperaciones())
                .atajadas(requestDTO.getAtajadas())
                .build();

        Estadistica guardada = repository.save(entidad);
        log.info("Estadística guardada exitosamente en BD con ID: {}", guardada.getId());

        return mapearAResponseDTO(guardada, "Estadísticas registradas con éxito");
    }

    public List<EstadisticaResponseDTO> listarTodas() {
        log.info("Consultando todas las estadísticas en la base de datos");
        return repository.findAll().stream()
                .map(entidad -> mapearAResponseDTO(entidad, "OK"))
                .collect(Collectors.toList());
    }

    public EstadisticaResponseDTO obtenerPorId(Long id) {
        log.info("Buscando estadística con ID: {}", id);
        Estadistica entidad = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron estadísticas con el ID: " + id));
        return mapearAResponseDTO(entidad, "OK");
    }

    public EstadisticaResponseDTO actualizar(Long id, EstadisticaRequestDTO requestDTO) {
        log.info("Actualizando estadística con ID: {}", id);
        validarLogicaDeEstadisticas(requestDTO);

        Estadistica entidadExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron estadísticas con el ID: " + id + " para actualizar"));

        entidadExistente.setJugadorId(requestDTO.getJugadorId());
        entidadExistente.setPartidoId(requestDTO.getPartidoId());
        entidadExistente.setGoles(requestDTO.getGoles());
        entidadExistente.setAsistencias(requestDTO.getAsistencias());
        entidadExistente.setIntercepciones(requestDTO.getIntercepciones());
        entidadExistente.setRecuperaciones(requestDTO.getRecuperaciones());
        entidadExistente.setAtajadas(requestDTO.getAtajadas());

        Estadistica actualizada = repository.save(entidadExistente);
        log.info("Estadística ID {} actualizada correctamente", id);

        return mapearAResponseDTO(actualizada, "Estadísticas actualizadas con éxito");
    }

    public void eliminar(Long id) {
        log.warn("Solicitud para eliminar la estadística con ID: {}", id);
        Estadistica entidad = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se puede eliminar. Estadística no encontrada con ID: " + id));

        repository.delete(entidad);
        log.info("Estadística ID {} eliminada exitosamente", id);
    }

    // Regla de negocio de validación
    private void validarLogicaDeEstadisticas(EstadisticaRequestDTO dto) {
        if (dto.getGoles() > 50) {
            log.error("Cantidad de goles irreal intentada: {}", dto.getGoles());
            throw new IllegalArgumentException("La cantidad de goles es irreal para un solo jugador en un partido.");
        }
    }

    private EstadisticaResponseDTO mapearAResponseDTO(Estadistica entidad, String estado) {
        return EstadisticaResponseDTO.builder()
                .id(entidad.getId())
                .jugadorId(entidad.getJugadorId())
                .partidoId(entidad.getPartidoId())
                .goles(entidad.getGoles())
                .asistencias(entidad.getAsistencias())
                .intercepciones(entidad.getIntercepciones())
                .recuperaciones(entidad.getRecuperaciones())
                .atajadas(entidad.getAtajadas())
                .estado(estado)
                .build();
    }
}