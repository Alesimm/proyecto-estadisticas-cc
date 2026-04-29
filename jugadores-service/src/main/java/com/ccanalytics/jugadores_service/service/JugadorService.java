package com.ccanalytics.jugadores_service.service;

import com.ccanalytics.jugadores_service.dto.JugadorRequestDTO;
import com.ccanalytics.jugadores_service.dto.JugadorResponseDTO;
import com.ccanalytics.jugadores_service.entity.Jugador;
import com.ccanalytics.jugadores_service.repository.JugadorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<JugadorResponseDTO> listarTodos() {
        return jugadorRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public JugadorResponseDTO guardarJugador(JugadorRequestDTO request) {
        log.info("Guardando jugador con camiseta: {}", request.getNumeroCamiseta());

        if (jugadorRepository.existsByNumeroCamiseta(request.getNumeroCamiseta())) {
            throw new IllegalArgumentException("El número de camiseta ya está ocupado");
        }

        Jugador jugador = new Jugador();
        jugador.setNombre(request.getNombre());
        jugador.setApellido(request.getApellido());
        jugador.setPosicion(request.getPosicion());
        jugador.setNumeroCamiseta(request.getNumeroCamiseta());

        Jugador guardado = jugadorRepository.save(jugador);
        return mapearAResponseDTO(guardado);
    }

    public void eliminarJugador(Long id) {
        if (!jugadorRepository.existsById(id)) {
            throw new IllegalArgumentException("El jugador no existe");
        }
        jugadorRepository.deleteById(id);
    }

    private JugadorResponseDTO mapearAResponseDTO(Jugador jugador) {
        JugadorResponseDTO dto = new JugadorResponseDTO();
        dto.setId(jugador.getId());
        dto.setNombre(jugador.getNombre());
        dto.setApellido(jugador.getApellido());
        dto.setPosicion(jugador.getPosicion());
        dto.setNumeroCamiseta(jugador.getNumeroCamiseta());
        return dto;
    }
}