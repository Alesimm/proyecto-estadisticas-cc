package com.cc.recomendaciones_service.service;

import com.cc.recomendaciones_service.client.EstadisticaClient;
import com.cc.recomendaciones_service.client.JugadorClient;
import com.cc.recomendaciones_service.client.RendimientoClient;
import com.cc.recomendaciones_service.dto.RecomendacionRequestDTO;
import com.cc.recomendaciones_service.dto.RecomendacionResponseDTO;
import com.cc.recomendaciones_service.entity.Recomendacion;
import com.cc.recomendaciones_service.repository.RecomendacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RecomendacionService {

    @Autowired
    private JugadorClient jugadorClient;

    @Autowired
    private EstadisticaClient estadisticaClient;

    @Autowired
    private RendimientoClient rendimientoClient;

    @Autowired
    private RecomendacionRepository repository;

    public RecomendacionResponseDTO generarAnalisis(RecomendacionRequestDTO request) {
        Long id = request.getIdJugador();
        log.info("Iniciando analisis tactico para el jugador ID: {}", id);

        Map<String, Object> jugador      = jugadorClient.obtenerJugador(id);
        Map<String, Object> estadisticas = estadisticaClient.obtenerEstadisticas(id);
        Map<String, Object> rendimiento  = rendimientoClient.obtenerNota(id);

        String nombreCompleto = jugador.get("nombre").toString() + " " + jugador.get("apellido").toString();
        Integer minutos = Integer.parseInt(estadisticas.get("minutosJugados").toString());
        Double nota     = Double.parseDouble(rendimiento.get("notaFinal").toString());

        // Reglas tacticas basadas en nota y minutos acumulados

        String sugerencia = "Mantener rotacion normal";
        String prioridad  = "BAJA";

        if (nota > 6.0 && minutos < 1500) {
            sugerencia = "Alinear como Titular Indiscutido";
            prioridad  = "ALTA";
        } else if (nota < 3.0) {
            sugerencia = "Enviar a entrenamiento especial";
            prioridad  = "MEDIA";
        } else if (minutos > 2000) {
            sugerencia = "Dar descanso por fatiga muscular";
            prioridad  = "ALTA";
        }

        // Guardar en la base de datos, si el jugador ya tiene recomendacion la actualiza
        Recomendacion rec = repository.findByIdJugador(id).orElse(new Recomendacion());
        rec.setIdJugador(id);
        rec.setNombreJugador(nombreCompleto);
        rec.setNotaRendimiento(nota);
        rec.setMinutosAcumulados(minutos);
        rec.setSugerenciaTactica(sugerencia);
        rec.setPrioridad(prioridad);

        Recomendacion guardado = repository.save(rec);
        log.info("Analisis finalizado exitosamente para: {} -> sugerencia: {}", nombreCompleto, sugerencia);

        return convertir(guardado);
    }

    // Retorna todas las recomendaciones guardadas en la BD
    public List<RecomendacionResponseDTO> listarTodos() {
        log.info("Listando todas las recomendaciones guardadas");
        return repository.findAll().stream()
                .map(this::convertir)
                .toList();
    }

    // Convertidor de entidad a DTO
    private RecomendacionResponseDTO convertir(Recomendacion r) {
        RecomendacionResponseDTO res = new RecomendacionResponseDTO();
        res.setIdJugador(r.getIdJugador());
        res.setNombreJugador(r.getNombreJugador());
        res.setNotaRendimiento(r.getNotaRendimiento());
        res.setMinutosAcumulados(r.getMinutosAcumulados());
        res.setSugerenciaTactica(r.getSugerenciaTactica());
        res.setPrioridad(r.getPrioridad());
        return res;
    }

}
