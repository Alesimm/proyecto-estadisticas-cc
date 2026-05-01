package com.cc.estadisticas_service.service;

import com.cc.estadisticas_service.dto.EstadisticaDTO;
import com.cc.estadisticas_service.model.Estadistica;
import com.cc.estadisticas_service.repository.EstadisticaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Agregamos los logs
@Service
public class EstadisticaService {

    private final EstadisticaRepository repository;

    // Inyección de dependencias por constructor (reemplaza a @Autowired)
    public EstadisticaService(EstadisticaRepository repository) {
        this.repository = repository;
    }

    public EstadisticaDTO guardar(EstadisticaDTO dto) {
        log.info("Iniciando guardado de estadísticas para el jugador ID: {}", dto.getJugadorId());

        Estadistica entidad = new Estadistica();
        entidad.setJugadorId(dto.getJugadorId());
        entidad.setPartidoId(dto.getPartidoId());
        entidad.setGoles(dto.getGoles());
        entidad.setAsistencias(dto.getAsistencias());
        entidad.setIntercepciones(dto.getIntercepciones());
        entidad.setRecuperaciones(dto.getRecuperaciones());
        entidad.setAtajadas(dto.getAtajadas());

        Estadistica guardada = repository.save(entidad);
        log.info("Estadística guardada exitosamente en BD con ID: {}", guardada.getId());

        dto.setId(guardada.getId());
        return dto;
    }

    public List<EstadisticaDTO> listarTodas() {
        log.info("Consultando todas las estadísticas en la base de datos");
        return repository.findAll().stream().map(entidad -> {
            EstadisticaDTO dto = new EstadisticaDTO();
            dto.setId(entidad.getId());
            dto.setJugadorId(entidad.getJugadorId());
            dto.setPartidoId(entidad.getPartidoId());
            dto.setGoles(entidad.getGoles());
            dto.setAsistencias(entidad.getAsistencias());
            dto.setIntercepciones(entidad.getIntercepciones());
            dto.setRecuperaciones(entidad.getRecuperaciones());
            dto.setAtajadas(entidad.getAtajadas());
            return dto;
        }).collect(Collectors.toList());
    }
}