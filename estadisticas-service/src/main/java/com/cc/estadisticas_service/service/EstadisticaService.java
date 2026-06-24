package com.cc.estadisticas_service.service;

import com.cc.estadisticas_service.client.JugadorClient;
import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.model.Estadistica;
import com.cc.estadisticas_service.repository.EstadisticaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadisticaService {

    private final EstadisticaRepository repository;
    private final JugadorClient jugadorClient;

    @Transactional
    public EstadisticaResponseDTO crear(EstadisticaRequestDTO dto) {
        if (repository.existsByIdJugador(dto.getIdJugador())) {
            throw new IllegalArgumentException("El jugador ya tiene un registro de estadisticas activo");
        }

        if (dto.getMinutosJugados() == 0 && (dto.getGolesTotales() > 0 || dto.getAsistencias() > 0 || dto.getRecuperaciones() > 0)) {
            throw new IllegalArgumentException("Los minutos jugados no pueden ser 0 si el jugador tiene goles, asistencias o recuperaciones");
        }

        try {
            jugadorClient.obtenerJugador(dto.getIdJugador());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al conectar con la API de Jugadores o el jugador no existe.");
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
        return mapearResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<EstadisticaResponseDTO> listar() {
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