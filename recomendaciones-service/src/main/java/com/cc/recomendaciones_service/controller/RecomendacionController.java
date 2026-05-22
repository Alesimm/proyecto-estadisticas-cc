package com.cc.recomendaciones_service.controller;

import com.cc.recomendaciones_service.dto.RecomendacionRequestDTO;
import com.cc.recomendaciones_service.dto.RecomendacionResponseDTO;
import com.cc.recomendaciones_service.service.RecomendacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
@CrossOrigin(origins = "*")
public class RecomendacionController {

    @Autowired
    private RecomendacionService service;

    // analiza un jugador consultando los 3 microservicios dependientes
    @PostMapping("/analizar")
    public ResponseEntity<RecomendacionResponseDTO> analizarJugador(@Valid @RequestBody RecomendacionRequestDTO request) {
        return new ResponseEntity<>(service.generarAnalisis(request), HttpStatus.CREATED);
    }

    // listar todas las recomendaciones guardadas en la BD
    @GetMapping
    public ResponseEntity<List<RecomendacionResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

}