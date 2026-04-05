package com.ccanalytics.jugadores_service.controller;

import com.ccanalytics.jugadores_service.entity.Jugador;
import com.ccanalytics.jugadores_service.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorRepository jugadorRepository;

    @GetMapping
    public List<Jugador> listarJugadores() {
        return jugadorRepository.findAll();
    }

    @PostMapping
    public Jugador guardarJugador(@RequestBody Jugador jugador) {
        return jugadorRepository.save(jugador);
    }
}
