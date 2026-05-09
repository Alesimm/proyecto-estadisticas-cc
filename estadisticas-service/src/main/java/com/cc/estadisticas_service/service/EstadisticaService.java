package com.cc.estadisticas_service.service;

import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.model.Estadistica;
import com.cc.estadisticas_service.repository.EstadisticaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadisticaService {
    private final EstadisticaRepository repository;

    public EstadisticaResponseDTO guardar(EstadisticaRequestDTO dto) {
        Estadistica e = new Estadistica();
        e.setJugadorId(dto.getJugadorId());
        e.setPartidoId(dto.getPartidoId());
        e.setGoles(dto.getGoles());
        e.setAsistencias(dto.getAsistencias());
        e.setIntercepciones(dto.getIntercepciones());
        e.setRecuperaciones(dto.getRecuperaciones());
        e.setAtajadas(dto.getAtajadas());

        Estadistica guardada = repository.save(e);
        return mapear(guardada, "Estadística creada");
    }

    public List<EstadisticaResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(e -> mapear(e, "OK"))
                .collect(Collectors.toList());
    }

    private EstadisticaResponseDTO mapear(Estadistica e, String msg) {
        return EstadisticaResponseDTO.builder()
                .id(e.getId()).jugadorId(e.getJugadorId()).partidoId(e.getPartidoId())
                .goles(e.getGoles()).asistencias(e.getAsistencias())
                .intercepciones(e.getIntercepciones()).recuperaciones(e.getRecuperaciones())
                .atajadas(e.getAtajadas()).mensaje(msg).build();
    }
}