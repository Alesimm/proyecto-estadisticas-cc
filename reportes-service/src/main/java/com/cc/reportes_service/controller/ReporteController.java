package com.cc.reportes_service.controller;

import com.cc.reportes_service.dto.ReporteRequestDTO;
import com.cc.reportes_service.dto.ReporteResponseDTO;
import com.cc.reportes_service.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crear(@Valid @RequestBody ReporteRequestDTO request) {
        return new ResponseEntity<>(service.generarReporte(request), HttpStatus.CREATED);
    }
}