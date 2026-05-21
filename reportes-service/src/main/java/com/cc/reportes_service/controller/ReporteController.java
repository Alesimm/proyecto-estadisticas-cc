package com.cc.reportes_service.controller;

import com.cc.reportes_service.dto.ReporteRequestDTO;
import com.cc.reportes_service.dto.ReporteResponseDTO;
import com.cc.reportes_service.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService service;

    // Genera un reporte nuevo consultando los 3 microservicios
    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crear(@Valid @RequestBody ReporteRequestDTO request) {
        return new ResponseEntity<>(service.generarReporte(request), HttpStatus.CREATED);
    }

    // Retorna todos los reportes guardados en la BD
    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // Retorna un reporte especifico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Elimina un reporte por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
