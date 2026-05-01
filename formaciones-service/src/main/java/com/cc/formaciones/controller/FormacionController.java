package com.cc.formaciones.controller;

import com.cc.formaciones.dto.FormacionDTO;
import com.cc.formaciones.service.FormacionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/formaciones")
public class FormacionController {

    private final FormacionService formacionService;

    public FormacionController(FormacionService formacionService) {
        this.formacionService = formacionService;
    }

    @PostMapping
    public ResponseEntity<FormacionDTO> crearFormacion(@Valid @RequestBody FormacionDTO formacionDTO) {
        log.info("Petición REST POST recibida en /api/formaciones");
        FormacionDTO nuevaFormacion = formacionService.crearFormacion(formacionDTO);
        return new ResponseEntity<>(nuevaFormacion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FormacionDTO>> obtenerTodas() {
        log.info("Petición REST GET recibida en /api/formaciones");
        List<FormacionDTO> formaciones = formacionService.obtenerTodas();
        return new ResponseEntity<>(formaciones, HttpStatus.OK);
    }
}