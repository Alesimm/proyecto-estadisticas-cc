package com.cc.estadisticas_service.controller;

import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.service.EstadisticaService;
import jakarta.validation.Valid;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> main
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
public class EstadisticaController {

    @Autowired
    private EstadisticaService service;

<<<<<<< HEAD
    @PostMapping
    public ResponseEntity<EstadisticaResponseDTO> crearEstadistica(@Valid @RequestBody EstadisticaRequestDTO requestDTO) {
        EstadisticaResponseDTO nuevaEstadistica = service.guardar(requestDTO);
        return new ResponseEntity<>(nuevaEstadistica, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EstadisticaResponseDTO>> listarEstadisticas() {
        List<EstadisticaResponseDTO> estadisticas = service.listarTodas();
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
=======
    @GetMapping
    public ResponseEntity<List<EstadisticaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<EstadisticaResponseDTO> guardar(@Valid @RequestBody EstadisticaRequestDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
>>>>>>> main
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadisticaResponseDTO> obtenerEstadisticaPorId(@PathVariable Long id) {
        EstadisticaResponseDTO response = service.obtenerPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadisticaResponseDTO> actualizarEstadistica(
            @PathVariable Long id,
            @Valid @RequestBody EstadisticaRequestDTO requestDTO) {
        EstadisticaResponseDTO response = service.actualizar(id, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstadistica(@PathVariable Long id) {
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}