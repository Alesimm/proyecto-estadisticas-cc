package com.cc.recomendacionesservice.exception;

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

    // Atrapa los errores cuando faltan datos en el JSON (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> erroresFormulario(MethodArgumentNotValidException problemita) {
        log.error("Hay un error en los campos del formulario");
        Map<String, String> errores = new HashMap<>();
        problemita.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // Atrapa nuestras reglas de negocio (ej. partido duplicado)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> erroresNegocio(IllegalArgumentException problemita) {
        log.error("Regla de negocio rota: {}", problemita.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", problemita.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Atrapa cualquier otro error para que no se caiga la app (caida de BD, etc)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> errorGrave(Exception problemita) {
        log.error("Fallo servidor ", problemita);
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ha surgido un problema...");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}