package com.cc.estadisticas_service.controller;

import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.service.EstadisticaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaController {

    @Autowired
    private EstadisticaService service;

    @GetMapping
    public ResponseEntity<List<EstadisticaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<EstadisticaResponseDTO> guardar(@Valid @RequestBody EstadisticaRequestDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }
}