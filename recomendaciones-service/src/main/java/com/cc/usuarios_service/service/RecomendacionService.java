package com.cc.usuarios_service.service;

import com.cc.usuarios_service.client.EstadisticaClient;
import com.cc.usuarios_service.client.JugadorClient;
import com.cc.usuarios_service.client.RendimientoClient;
import com.cc.usuarios_service.dto.RecomendacionRequestDTO;
import com.cc.usuarios_service.dto.RecomendacionResponseDTO;
import com.cc.usuarios_service.entity.Recomendacion;
import com.cc.usuarios_service.repository.RecomendacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class RecomendacionService {

    @Autowired
    private RecomendacionRepository recomendacionRepository;

    @Autowired
    private JugadorClient jugadorClient;

    @Autowired
    private RendimientoClient rendimientoClient;

    @Autowired
    private EstadisticaClient estadisticaClient;

    public RecomendacionResponseDTO analizarJugador(RecomendacionRequestDTO request) {
        Long idJugador = request.getIdJugador();
        log.info("Iniciando analisis de recomendacion para el jugador ID: {}", idJugador);

        Map<String, Object> jugadorMap;
        Map<String, Object> rendimientoMap;
        Map<String, Object> estadisticaMap;

        // 1. Blindaje y llamada a Jugadores (8081)
        try {
            jugadorMap = jugadorClient.obtenerJugador(idJugador);
            if (jugadorMap == null) {
                log.error("No se encontro el jugador con ID: {}", idJugador);
                throw new IllegalArgumentException("Error de negocio: Jugador no encontrado en el sistema base");
            }
        } catch (IllegalArgumentException e) {
            throw e; // Relanza nuestro error controlado
        } catch (Exception e) {
            log.error("Conexion rechazada con Jugadores-Service");
            throw new IllegalArgumentException("Error de negocio: El servicio de Jugadores esta apagado o inalcanzable");
        }

        // 2. Blindaje y llamada a Rendimiento (8087)
        try {
            rendimientoMap = rendimientoClient.obtenerRendimiento(idJugador);
        } catch (Exception e) {
            log.error("Conexion rechazada con Rendimiento-Service");
            throw new IllegalArgumentException("Error de negocio: El servicio de Rendimiento esta apagado o inalcanzable");
        }

        // 3. Blindaje y llamada a Estadisticas (8083)
        try {
            estadisticaMap = estadisticaClient.obtenerEstadistica(idJugador);
        } catch (Exception e) {
            log.error("Conexion rechazada con Estadisticas-Service");
            throw new IllegalArgumentException("Error de negocio: El servicio de Estadisticas esta apagado o inalcanzable");
        }

        // 4. Extraccion de datos adaptada a los nombres exactos de tu Base de Datos
        String nombre = jugadorMap.get("nombre") != null ? jugadorMap.get("nombre").toString() : "Desconocido";

        Object objNota = rendimientoMap != null && rendimientoMap.get("nota_final") != null
                ? rendimientoMap.get("nota_final") : (rendimientoMap != null ? rendimientoMap.get("notaFinal") : null);
        Double nota = objNota != null ? Double.parseDouble(objNota.toString()) : 0.0;

        Object objMinutos = estadisticaMap != null && estadisticaMap.get("minutos_jugados") != null
                ? estadisticaMap.get("minutos_jugados") : (estadisticaMap != null ? estadisticaMap.get("minutosJugados") : null);
        Integer minutos = objMinutos != null ? Integer.parseInt(objMinutos.toString()) : 0;

        String sugerencia;
        String prioridad;

        // 5. Algoritmo Tactico
        if (nota > 6.0 && minutos < 1500) {
            sugerencia = "Alinear como Titular Indiscutido";
            prioridad = "ALTA";
        } else if (nota < 3.0) {
            sugerencia = "Enviar a entrenamiento especial";
            prioridad = "MEDIA";
        } else if (minutos > 2000) {
            sugerencia = "Dar descanso por fatiga muscular";
            prioridad = "ALTA";
        } else {
            sugerencia = "Mantener rotacion normal";
            prioridad = "BAJA";
        }

        log.info("Algoritmo finalizado. Sugerencia: {} | Prioridad: {}", sugerencia, prioridad);

        // 6. Guardar en Base de Datos (Sobrescribe si ya existe una recomendacion para ese jugador)
        Recomendacion recomendacion = recomendacionRepository.findByIdJugador(idJugador).orElse(new Recomendacion());
        recomendacion.setIdJugador(idJugador);
        recomendacion.setNombreJugador(nombre);
        recomendacion.setNotaRendimiento(nota);
        recomendacion.setMinutosAcumulados(minutos);
        recomendacion.setSugerenciaTactica(sugerencia);
        recomendacion.setPrioridad(prioridad);

        Recomendacion guardada = recomendacionRepository.save(recomendacion);
        return mapearADTO(guardada);
    }

    private RecomendacionResponseDTO mapearADTO(Recomendacion recomendacion) {
        RecomendacionResponseDTO dto = new RecomendacionResponseDTO();
        dto.setId(recomendacion.getId());
        dto.setIdJugador(recomendacion.getIdJugador());
        dto.setNombreJugador(recomendacion.getNombreJugador());
        dto.setNotaRendimiento(recomendacion.getNotaRendimiento());
        dto.setMinutosAcumulados(recomendacion.getMinutosAcumulados());
        dto.setSugerenciaTactica(recomendacion.getSugerenciaTactica());
        dto.setPrioridad(recomendacion.getPrioridad());
        return dto;
    }
}