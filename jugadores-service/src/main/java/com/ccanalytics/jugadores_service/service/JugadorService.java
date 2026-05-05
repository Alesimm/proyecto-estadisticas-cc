package com.ccanalytics.jugadores_service.service;

import com.ccanalytics.jugadores_service.dto.JugadorRequestDTO;
import com.ccanalytics.jugadores_service.dto.JugadorResponseDTO;
import com.ccanalytics.jugadores_service.entity.Jugador;
import com.ccanalytics.jugadores_service.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    // lista todos los jugadores
    public List<JugadorResponseDTO> listarTodos() {
        return jugadorRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    // busca un jugador por su id
    public JugadorResponseDTO buscarPorId(Long id) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontro ningun jugador con el ID: " + id));
        return mapearAResponseDTO(jugador);
    }

    // busca la lista de jugadores que jueguen en x posicion
    public List<JugadorResponseDTO> buscarPorPosicion(String posicion) {
        List<Jugador> jugadores = jugadorRepository.findByPosicionIgnoreCase(posicion);
        if (jugadores.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron jugadores en la posicion: " + posicion);
        }
        return jugadores.stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    // guarda un jugador validando que no se repita la camiseta
    public JugadorResponseDTO guardarJugador(JugadorRequestDTO request) {
        if (jugadorRepository.existsByNumeroCamiseta(request.getNumeroCamiseta())) {
            throw new IllegalArgumentException("El numero de camiseta ya esta ocupado");
        }

        Jugador jugador = new Jugador();
        jugador.setNombre(request.getNombre());
        jugador.setApellido(request.getApellido());
        jugador.setPosicion(request.getPosicion());
        jugador.setNumeroCamiseta(request.getNumeroCamiseta());

        Jugador guardado = jugadorRepository.save(jugador);
        return mapearAResponseDTO(guardado);
    }

    // elimina usando el id
    public void eliminarJugador(Long id) {
        if (!jugadorRepository.existsById(id)) {
            throw new IllegalArgumentException("El jugador no existe");
        }
        jugadorRepository.deleteById(id);
    }

    // pasa de entidad a dto para no mostrar la base de datos directo
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