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

@Service
@Slf4j
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<JugadorResponseDTO> listarTodos() {
        log.info("Buscando a todos los jugadores del plantel");
        return jugadorRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public JugadorResponseDTO buscarPorId(Long id) {
        log.info("Buscando datos del jugador con ID {}", id);
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No tenemos a ningun jugador con el ID: " + id));
        return mapearAResponseDTO(jugador);
    }

    public List<JugadorResponseDTO> buscarPorPosicion(String posicion) {
        log.info("Filtrando jugadores por la posicion: {}", posicion);
        return jugadorRepository.findByPosicionIgnoreCase(posicion).stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    public JugadorResponseDTO guardarJugador(JugadorRequestDTO request) {
        log.info("Iniciando registro de nuevo jugador: {} {}", request.getNombre(), request.getApellido());

        // Regla de Negocio 1
        if (jugadorRepository.existsByNumeroCamiseta(request.getNumeroCamiseta())) {
            log.warn("Fallo el registro: el dorsal {} ya esta siendo ocupado", request.getNumeroCamiseta());
            throw new IllegalArgumentException("Ese numero de camiseta ya lo tiene otro jugador");
        }

        Jugador jugador = new Jugador();
        jugador.setNombre(request.getNombre());
        jugador.setApellido(request.getApellido());
        jugador.setPosicion(request.getPosicion());
        jugador.setNumeroCamiseta(request.getNumeroCamiseta());
        jugador.setNacionalidad(request.getNacionalidad());
        jugador.setEdad(request.getEdad());

        Jugador guardado = jugadorRepository.save(jugador);
        log.info("Jugador registrado con exito. Se le asigno el ID {}", guardado.getId());

        return mapearAResponseDTO(guardado);
    }

    public void eliminarJugador(Long id) {
        if (!jugadorRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: el jugador no existe");
        }
        jugadorRepository.deleteById(id);
        log.info("Jugador con ID {} fue eliminado del sistema", id);
    }

    // pasamos de entidad a dto para no exponer la base de datos directo a la web
    private JugadorResponseDTO mapearAResponseDTO(Jugador jugador) {
        JugadorResponseDTO dto = new JugadorResponseDTO();
        dto.setId(jugador.getId());
        dto.setNombre(jugador.getNombre());
        dto.setApellido(jugador.getApellido());
        dto.setPosicion(jugador.getPosicion());
        dto.setNumeroCamiseta(jugador.getNumeroCamiseta());
        dto.setNacionalidad(jugador.getNacionalidad());
        dto.setEdad(jugador.getEdad());
        return dto;
    }
}