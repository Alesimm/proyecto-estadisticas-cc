package com.ccanalytics.jugadores_service.controller;

import com.ccanalytics.jugadores_service.dto.JugadorRequestDTO;
import com.ccanalytics.jugadores_service.dto.JugadorResponseDTO;
import com.ccanalytics.jugadores_service.service.JugadorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@Slf4j
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<List<JugadorResponseDTO>> listarJugadores() {
        log.info("Peticion entrante: Mostrar todos los jugadores");
        return ResponseEntity.ok(jugadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponseDTO> obtenerJugadorPorId(@PathVariable Long id) {
        log.info("Peticion entrante: Mostrar jugador ID {}", id);
        return ResponseEntity.ok(jugadorService.buscarPorId(id));
    }

    @GetMapping("/posicion/{posicion}")
    public ResponseEntity<List<JugadorResponseDTO>> buscarPorPosicion(@PathVariable String posicion) {
        log.info("Peticion entrante: Buscar jugadores por posicion {}", posicion);
        return ResponseEntity.ok(jugadorService.buscarPorPosicion(posicion));
    }

    @PostMapping
    public ResponseEntity<JugadorResponseDTO> guardarJugador(@Valid @RequestBody JugadorRequestDTO requestDTO) {
        log.info("Peticion entrante: Guardar nuevo jugador");
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.guardarJugador(requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable Long id) {
        log.info("Peticion entrante: Eliminar jugador ID {}", id);
        jugadorService.eliminarJugador(id);
        return ResponseEntity.noContent().build();
    }
}