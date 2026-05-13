package com.cc.rendimiento_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Atrapamos cuando el JSON viene vacio o incompleto
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacion(MethodArgumentNotValidException problemita) {
        Map<String, String> errores = new HashMap<>();
        problemita.getBindingResult().getFieldErrors().forEach(e ->
                errores.put(e.getField(), e.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }

    // Atrapamos problemas de logica o de comunicacion con los otros servicios
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> negocio(IllegalArgumentException problemita) {
        Map<String, String> error = new HashMap<>();
        error.put("error", problemita.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // Un paracaidas general por si el servidor colapsa
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> errorGeneral(Exception problemita) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "El jugador no existe en la base de datos central.");
        return ResponseEntity.internalServerError().body(error);
    }
}