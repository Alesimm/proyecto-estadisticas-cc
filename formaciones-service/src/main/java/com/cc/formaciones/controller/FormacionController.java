package com.cc.formaciones.controller;

import com.cc.formaciones.entity.Formacion;
import com.cc.formaciones.service.FormacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formaciones")
public class FormacionController {

    private final FormacionService formacionService;

    public FormacionController(FormacionService formacionService) {
        this.formacionService = formacionService;
    }

    @PostMapping
    public ResponseEntity<Formacion> crearFormacion(@RequestBody Formacion formacion) {
        Formacion nuevaFormacion = formacionService.crearFormacion(formacion);
        return new ResponseEntity<>(nuevaFormacion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Formacion>> obtenerTodas() {
        List<Formacion> formaciones = formacionService.obtenerTodas();
        return new ResponseEntity<>(formaciones, HttpStatus.OK);
    }
}