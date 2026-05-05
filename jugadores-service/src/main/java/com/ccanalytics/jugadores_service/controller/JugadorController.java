package com.ccanalytics.jugadores_service.controller;

import com.ccanalytics.jugadores_service.dto.JugadorRequestDTO;
import com.ccanalytics.jugadores_service.dto.JugadorResponseDTO;
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

    // traer todos
    @GetMapping
    public ResponseEntity<List<JugadorResponseDTO>> listarJugadores() {
        return ResponseEntity.ok(jugadorService.listarTodos());
    }

    // traer por id
    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponseDTO> obtenerJugadorPorId(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.buscarPorId(id));
    }

    // traer por posicion (ej: /api/jugadores/posicion/delantero)
    @GetMapping("/posicion/{posicion}")
    public ResponseEntity<List<JugadorResponseDTO>> buscarPorPosicion(@PathVariable String posicion) {
        return ResponseEntity.ok(jugadorService.buscarPorPosicion(posicion));
    }

    // guardar nuevo jugador
    @PostMapping
    public ResponseEntity<JugadorResponseDTO> guardarJugador(@Valid @RequestBody JugadorRequestDTO requestDTO) {
        JugadorResponseDTO nuevo = jugadorService.guardarJugador(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable Long id) {
        jugadorService.eliminarJugador(id);
        return ResponseEntity.noContent().build();
    }
}