package com.cc.partidosservice.controller;

import com.cc.partidosservice.dto.PartidoRequestDTO;
import com.cc.partidosservice.dto.PartidoResponseDTO;
import com.cc.partidosservice.service.PartidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
@Slf4j
public class PartidoController {

    private final PartidoService partidoService;

    @PostMapping
    public ResponseEntity<PartidoResponseDTO> crearPartido(@Valid @RequestBody PartidoRequestDTO dto) {
        log.info("Recepcion de peticion POST para crear un partido");
        return new ResponseEntity<>(partidoService.registrarPartido(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PartidoResponseDTO>> listarPartidos() {
        log.info("Recepcion de peticion GET para listar partidos");
        return ResponseEntity.ok(partidoService.obtenerTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPartido(@PathVariable Long id) {
        log.info("Recepcion de peticion DELETE para eliminar el partido con ID: {}", id);
        partidoService.eliminarPartido(id);
        return ResponseEntity.noContent().build();
    }
}