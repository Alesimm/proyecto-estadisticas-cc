package com.cc.partidosservice.controller;

import com.cc.partidosservice.dto.PartidoRequestDTO;
import com.cc.partidosservice.dto.PartidoResponseDTO;
import com.cc.partidosservice.service.PartidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partidos")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    @GetMapping
    public ResponseEntity<List<PartidoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(partidoService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<PartidoResponseDTO> crear(@Valid @RequestBody PartidoRequestDTO partido) {
        return ResponseEntity.status(201).body(partidoService.guardar(partido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        partidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}