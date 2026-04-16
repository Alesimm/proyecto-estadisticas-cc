package com.ccanalytics.jugadores_service.service;

import com.ccanalytics.jugadores_service.entity.Jugador;
import com.ccanalytics.jugadores_service.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Jugador> listarTodos() {
        return jugadorRepository.findAll();
    }

    public Jugador guardarJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Optional<Jugador> buscarPorId(Long id) {
        return jugadorRepository.findById(id);
    }
}