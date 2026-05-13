package com.cc.lesiones_service.service;

import com.cc.lesiones_service.dto.LesionRequestDTO;
import com.cc.lesiones_service.dto.LesionResponseDTO;
import com.cc.lesiones_service.model.Lesion;
import com.cc.lesiones_service.repository.LesionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LesionService {

    private final LesionRepository repository;

    @Transactional
    public LesionResponseDTO registrarLesion(LesionRequestDTO dto) {
        log.info("Iniciando registro de lesion para el jugador con ID: {}", dto.getIdJugador());

        // REGLA 1: No permitir nueva lesion si ya esta "En Tratamiento"
        if ("En Tratamiento".equalsIgnoreCase(dto.getEstadoMedico()) &&
                repository.existsByIdJugadorAndEstadoMedico(dto.getIdJugador(), "En Tratamiento")) {
            log.warn("Rechazado: El jugador ID {} ya tiene una lesion activa en tratamiento", dto.getIdJugador());
            throw new IllegalArgumentException("El jugador ya esta de baja medica con estado En Tratamiento");
        }

        // REGLA 2: Forzar dias a 0 si es "Alta Medica"
        int diasFinales = dto.getDiasRecuperacion();
        if ("Alta Medica".equalsIgnoreCase(dto.getEstadoMedico())) {
            log.info("Detectada Alta Medica: seteando dias de recuperacion a 0 forzosamente");
            diasFinales = 0;
        }

        Lesion lesion = Lesion.builder()
                .idJugador(dto.getIdJugador())
                .tipoLesion(dto.getTipoLesion())
                .gradoGravedad(dto.getGradoGravedad())
                .fechaLesion(dto.getFechaLesion())
                .diasRecuperacion(diasFinales)
                .estadoMedico(dto.getEstadoMedico())
                .build();

        Lesion guardada = repository.save(lesion);
        log.info("Lesion guardada exitosamente. ID generado: {}", guardada.getId());

        return mapearResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<LesionResponseDTO> listar() {
        log.info("Obteniendo listado historico de lesiones");
        return repository.findAll().stream().map(this::mapearResponse).collect(Collectors.toList());
    }

    private LesionResponseDTO mapearResponse(Lesion e) {
        return LesionResponseDTO.builder()
                .id(e.getId())
                .idJugador(e.getIdJugador())
                .tipoLesion(e.getTipoLesion())
                .gradoGravedad(e.getGradoGravedad())
                .fechaLesion(e.getFechaLesion())
                .diasRecuperacion(e.getDiasRecuperacion())
                .estadoMedico(e.getEstadoMedico())
                .build();
    }
}