package com.cc.usuarios_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> interceptarValidaciones(MethodArgumentNotValidException problemita) {
        log.error("Capturando error de validacion de DTO en peticion entrante");
        Map<String, String> errores = new HashMap<>();
        problemita.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> interceptarErroresDeNegocio(IllegalArgumentException problemita) {
        log.error("Capturando violacion de regla de negocio: {}", problemita.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", problemita.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> fallbackGeneral(Exception problemita) {
        log.error("Capturando caida critica inesperada en Usuarios Service", problemita);
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrio un problema interno. Intente mas tarde.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}