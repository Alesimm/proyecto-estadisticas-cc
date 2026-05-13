package com.cc.lesiones_service.controller;

import com.cc.lesiones_service.dto.LesionRequestDTO;
import com.cc.lesiones_service.dto.LesionResponseDTO;
import com.cc.lesiones_service.service.LesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesiones")
@RequiredArgsConstructor
public class LesionController {

    private final LesionService service;

    @PostMapping
    public ResponseEntity<LesionResponseDTO> registrar(@Valid @RequestBody LesionRequestDTO request) {
        return new ResponseEntity<>(service.registrarLesion(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LesionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}