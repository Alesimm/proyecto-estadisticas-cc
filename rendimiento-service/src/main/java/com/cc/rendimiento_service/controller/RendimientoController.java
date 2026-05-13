package com.cc.rendimiento_service.controller;

import com.cc.rendimiento_service.dto.*;
import com.cc.rendimiento_service.service.RendimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rendimientos")
@CrossOrigin(origins = "*") // Abrimos las puertas para que no haya bloqueos de CORS
public class RendimientoController {

    @Autowired
    private RendimientoService service;

    // Endpoint unico: Entra un ID, sale una boleta de rendimiento completa
    @PostMapping("/calcular")
    public ResponseEntity<RendimientoResponseDTO> calcular(@Valid @RequestBody RendimientoRequestDTO request) {
        return new ResponseEntity<>(service.calcular(request), HttpStatus.CREATED);
    }
}