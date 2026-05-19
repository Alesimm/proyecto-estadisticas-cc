package com.cc.partidos_service.service;

import com.cc.partidos_service.dto.PartidoRequestDTO;
import com.cc.partidos_service.dto.PartidoResponseDTO;
import com.cc.partidos_service.entity.Partido;
import com.cc.partidos_service.repository.PartidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartidoService {

    private final PartidoRepository partidoRepository;

    public PartidoResponseDTO registrarPartido(PartidoRequestDTO dto) {
        log.info("Iniciando registro de partido contra: {}", dto.getRival());

        // Regla 1: No jugar contra si mismo
        if (dto.getRival().trim().equalsIgnoreCase("Colo-Colo") || dto.getRival().trim().equalsIgnoreCase("Colo Colo")) {
            log.error("Intento invalido: Se intento registrar un partido contra Colo-Colo");
            throw new IllegalArgumentException("Error de negocio: Colo-Colo no puede jugar contra si mismo");
        }

        // Regla 2: Evitar duplicados exactos
        if (partidoRepository.existsByRivalAndTorneoAndFecha(dto.getRival(), dto.getTorneo(), dto.getFecha())) {
            log.error("Intento duplicado: Ya existe un partido contra {} en el torneo {} para la fecha {}", dto.getRival(), dto.getTorneo(), dto.getFecha());
            throw new IllegalArgumentException("Error de negocio: Este partido ya se encuentra registrado en el sistema");
        }

        Partido partido = new Partido();
        partido.setRival(dto.getRival());
        partido.setTorneo(dto.getTorneo());
        partido.setFecha(dto.getFecha());
        partido.setGolesColoColo(dto.getGolesColoColo());
        partido.setGolesRival(dto.getGolesRival());
        partido.setEstado(dto.getEstado());

        Partido partidoGuardado = partidoRepository.save(partido);
        log.info("Partido registrado con exito. ID: {}", partidoGuardado.getId());

        return mapearADTO(partidoGuardado);
    }

    public List<PartidoResponseDTO> obtenerTodos() {
        log.info("Consultando todos los partidos registrados en el sistema");
        return partidoRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public void eliminarPartido(Long id) {
        log.info("Iniciando eliminacion del partido con ID: {}", id);

        if (!partidoRepository.existsById(id)) {
            log.error("Intento fallido: El partido con ID {} no existe en el sistema", id);
            throw new IllegalArgumentException("No se puede eliminar: El partido con el ID especificado no existe");
        }

        partidoRepository.deleteById(id);
        log.info("Partido con ID {} eliminado exitosamente", id);
    }

    private PartidoResponseDTO mapearADTO(Partido partido) {
        PartidoResponseDTO dto = new PartidoResponseDTO();
        dto.setId(partido.getId());
        dto.setRival(partido.getRival());
        dto.setTorneo(partido.getTorneo());
        dto.setFecha(partido.getFecha());
        dto.setGolesColoColo(partido.getGolesColoColo());
        dto.setGolesRival(partido.getGolesRival());
        dto.setEstado(partido.getEstado());
        return dto;
    }
}