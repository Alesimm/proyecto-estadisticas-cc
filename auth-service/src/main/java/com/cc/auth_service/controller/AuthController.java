package com.cc.auth_service.controller;

import com.cc.auth_service.dto.AuthRequestDTO;
import com.cc.auth_service.dto.AuthResponseDTO;
import com.cc.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> iniciarSesion(@Valid @RequestBody AuthRequestDTO request) {
        log.info("Peticion POST a /api/auth/login recibida");
        return ResponseEntity.ok(authService.login(request));
    }
}