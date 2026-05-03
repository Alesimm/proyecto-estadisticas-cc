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

    private final FormacionService formacionService;

    @PostMapping
    public ResponseEntity<FormacionResponseDTO> crearFormacion(@Valid @RequestBody FormacionRequestDTO requestDTO) {
        FormacionResponseDTO response = formacionService.registrarFormacion(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FormacionResponseDTO>> listarFormaciones() {
        List<FormacionResponseDTO> formaciones = formacionService.obtenerTodas();
        return new ResponseEntity<>(formaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormacionResponseDTO> obtenerFormacionPorId(@PathVariable Long id) {
        FormacionResponseDTO response = formacionService.obtenerPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormacionResponseDTO> actualizarFormacion(
            @PathVariable Long id,
            @Valid @RequestBody FormacionRequestDTO requestDTO) {
        FormacionResponseDTO response = formacionService.actualizarFormacion(id, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFormacion(@PathVariable Long id) {
        formacionService.eliminarFormacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}