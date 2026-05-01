package com.cc.estadisticas_service.controller;

import com.cc.estadisticas_service.dto.EstadisticaDTO;
import com.cc.estadisticas_service.service.EstadisticaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaController {

    private final EstadisticaService service;

    public EstadisticaController(EstadisticaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EstadisticaDTO> crearEstadistica(@Valid @RequestBody EstadisticaDTO estadisticaDTO) {
        log.info("Petición REST POST recibida para crear estadística");
        EstadisticaDTO nuevaEstadistica = service.guardar(estadisticaDTO);
        return new ResponseEntity<>(nuevaEstadistica, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EstadisticaDTO>> listarEstadisticas() {
        log.info("Petición REST GET recibida en /api/estadisticas");
        List<EstadisticaDTO> estadisticas = service.listarTodas();
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
}