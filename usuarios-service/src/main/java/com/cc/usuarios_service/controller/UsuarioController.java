package com.cc.usuarios_service.controller;

import com.cc.usuarios_service.dto.UsuarioRequestDTO;
import com.cc.usuarios_service.dto.UsuarioResponseDTO;
import com.cc.usuarios_service.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        log.info("Peticion GET recibida en /api/usuarios");
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        log.info("Peticion POST recibida en /api/usuarios");
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(request));
    }
}