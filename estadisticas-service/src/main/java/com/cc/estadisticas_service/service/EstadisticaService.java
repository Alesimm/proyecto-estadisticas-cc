package com.cc.estadisticas_service.service;

import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.model.Estadistica;
import com.cc.estadisticas_service.repository.EstadisticaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstadisticaService {

    private final EstadisticaRepository repository;

    @Transactional
    public EstadisticaResponseDTO crear(EstadisticaRequestDTO dto) {
        log.info("Iniciando validaciones para registrar estadisticas del jugador id: {}", dto.getIdJugador());

        if (repository.existsByIdJugador(dto.getIdJugador())) {
            log.error("Rechazado: El jugador id {} ya tiene estadisticas en la base de datos", dto.getIdJugador());
            throw new IllegalArgumentException("El jugador ya tiene un registro de estadisticas activo");
        }

        if (dto.getMinutosJugados() == 0 && (dto.getGolesTotales() > 0 || dto.getAsistencias() > 0 || dto.getRecuperaciones() > 0)) {
            log.error("Rechazado: Inconsistencia de datos. Minutos jugados es 0 pero registra acciones en cancha");
            throw new IllegalArgumentException("Los minutos jugados no pueden ser 0 si el jugador tiene goles, asistencias o recuperaciones");
        }

        Estadistica estadistica = Estadistica.builder()
                .idJugador(dto.getIdJugador())
                .minutosJugados(dto.getMinutosJugados())
                .golesTotales(dto.getGolesTotales())
                .asistencias(dto.getAsistencias())
                .recuperaciones(dto.getRecuperaciones())
                .golesRecibidos(dto.getGolesRecibidos())
                .build();

        Estadistica guardada = repository.save(estadistica);
        log.info("Estadisticas creadas con exito. Nuevo ID asignado: {}", guardada.getId());

        return mapearResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<EstadisticaResponseDTO> listar() {
        log.info("Consultando el listado completo de estadisticas");
        return repository.findAll().stream().map(this::mapearResponse).collect(Collectors.toList());
    }

    private EstadisticaResponseDTO mapearResponse(Estadistica model) {
        return EstadisticaResponseDTO.builder()
                .id(model.getId())
                .idJugador(model.getIdJugador())
                .minutosJugados(model.getMinutosJugados())
                .golesTotales(model.getGolesTotales())
                .asistencias(model.getAsistencias())
                .recuperaciones(model.getRecuperaciones())
                .golesRecibidos(model.getGolesRecibidos())
                .build();
    }
}