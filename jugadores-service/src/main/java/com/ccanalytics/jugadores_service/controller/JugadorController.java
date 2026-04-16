package com.ccanalytics.jugadores_service.controller;

import com.ccanalytics.jugadores_service.entity.Jugador;
import com.ccanalytics.jugadores_service.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<List<Jugador>> listarJugadores() {
        return ResponseEntity.ok(jugadorService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Jugador> guardarJugador(@Valid @RequestBody Jugador jugador) {
        Jugador nuevoJugador = jugadorService.guardarJugador(jugador);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoJugador);
    }
}