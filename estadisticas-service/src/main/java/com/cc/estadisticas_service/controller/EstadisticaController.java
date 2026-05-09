package com.cc.estadisticas_service.controller;

import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.service.EstadisticaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
public class EstadisticaController {

    private final EstadisticaService service;

    @PostMapping
    public ResponseEntity<EstadisticaResponseDTO> crearEstadistica(@Valid @RequestBody EstadisticaRequestDTO request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EstadisticaResponseDTO>> listarEstadisticas() {
        return ResponseEntity.ok(service.listar());
    }
}