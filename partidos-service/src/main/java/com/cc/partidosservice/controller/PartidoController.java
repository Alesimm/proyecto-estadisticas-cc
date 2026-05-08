package com.cc.partidosservice.controller;

import com.cc.partidosservice.dto.PartidoRequestDTO;
import com.cc.partidosservice.dto.PartidoResponseDTO;
import com.cc.partidosservice.service.PartidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone la API pública para la gestión de partidos.
 * Solo orquesta el flujo de datos: recibe peticiones HTTP y delega la lógica al servicio.
 * Cumple con los estándares REST semánticos (IE 2.4.2).
 *
 * @author Joseph Rivas
 */
@Slf4j // Logs para la capa de presentación.
@RestController
@RequestMapping("/partidos") // Ruta base semántica (IE 2.4.2).
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    /**
     * Endpoint GET para recuperar la lista completa de partidos.
     *
     * @return ResponseEntity con código 200 OK y la lista de DTOs.
     */
    @GetMapping
    public ResponseEntity<List<PartidoResponseDTO>> obtenerTodos() {
        log.info("Petición REST recibida: GET /partidos (Recuperar todos).");
        return ResponseEntity.ok(partidoService.obtenerTodos());
    }

    /**
     * Endpoint POST para programar un nuevo partido.
     *
     * @param partido Datos validados del nuevo partido. Se usa @Valid para activar Bean Validation.
     * @return ResponseEntity con código 201 Created y el DTO creado.
     */
    @PostMapping
    public ResponseEntity<PartidoResponseDTO> crear(@Valid @RequestBody PartidoRequestDTO partido) {
        log.info("Petición REST recibida: POST /partidos (Crear nuevo partido contra: {}).", partido.getRival());
        // Se devuelve 201 Created para indicar la creación exitosa del recurso (IE 2.4.2).
        return ResponseEntity.status(201).body(partidoService.guardar(partido));
    }

    /**
     * Endpoint PUT para actualizar un partido existente (CRUD Completo/IE 2.1.2).
     *
     * @param id      ID del partido pasado en la URL.
     * @param partido Datos validados actualizados.
     * @return ResponseEntity con código 200 OK y el DTO actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartidoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PartidoRequestDTO partido) {
        log.info("Petición REST recibida: PUT /partidos/{} (Actualizar partido).", id);
        return ResponseEntity.ok(partidoService.actualizar(id, partido));
    }

    /**
     * Endpoint DELETE para cancelar/eliminar un partido por su ID.
     *
     * @param id ID del partido pasado en la URL.
     * @return ResponseEntity con código 204 No Content para indicar eliminación exitosa y sin cuerpo de respuesta (REST Semántico/IE 2.4.2).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Petición REST recibida: DELETE /partidos/{} (Eliminar partido).", id);
        partidoService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content.
    }
}