package com.cc.formaciones.controller;

import com.cc.formaciones.dto.FormacionRequestDTO;
import com.cc.formaciones.dto.FormacionResponseDTO;
import com.cc.formaciones.service.FormacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formaciones")
@RequiredArgsConstructor
public class FormacionController {

    private final FormacionService service;

    @PostMapping
    public ResponseEntity<FormacionResponseDTO> crearFormacion(@Valid @RequestBody FormacionRequestDTO request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FormacionResponseDTO>> listarFormaciones() {
        return ResponseEntity.ok(service.listar());
    }
}