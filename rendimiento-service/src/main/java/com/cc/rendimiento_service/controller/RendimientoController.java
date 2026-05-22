package com.cc.rendimiento_service.controller;

import com.cc.rendimiento_service.dto.*;
import com.cc.rendimiento_service.service.RendimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendimientos")
@CrossOrigin(origins = "*")
public class RendimientoController {

    @Autowired
    private RendimientoService service;

    // calcula y guarda el rendimiento de un jugador
    @PostMapping("/calcular")
    public ResponseEntity<RendimientoResponseDTO> calcular(@Valid @RequestBody RendimientoRequestDTO request) {
        return new ResponseEntity<>(service.calcular(request), HttpStatus.CREATED);
    }

    // listar todos los rendimientos guardados
    @GetMapping
    public ResponseEntity<List<RendimientoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

}