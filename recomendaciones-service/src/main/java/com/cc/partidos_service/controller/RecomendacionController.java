package com.cc.partidos_service.controller;

import com.cc.partidos_service.dto.RecomendacionRequestDTO;
import com.cc.partidos_service.dto.RecomendacionResponseDTO;
import com.cc.partidos_service.service.RecomendacionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recomendaciones")
@Slf4j
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

    @PostMapping("/analizar")
    public ResponseEntity<RecomendacionResponseDTO> analizarJugador(@Valid @RequestBody RecomendacionRequestDTO request) {
        log.info("Recepcion de peticion POST para analizar recomendacion de jugador");
        return new ResponseEntity<>(recomendacionService.analizarJugador(request), HttpStatus.CREATED);
    }
}