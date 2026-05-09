package com.cc.partidosservice.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones.
 * Captura errores en cualquier controlador y devuelve respuestas JSON estructuradas (IE 2.3.1).
 */
@Slf4j // Logs para monitorear qué errores están ocurriendo en el sistema
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Error de validación de campos (400 Bad Request) detectado.");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    // NUEVO: Manejo de Entidades no encontradas (HTTP 404)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Recurso no encontrado (404 Not Found): {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", "Recurso no encontrado");
        error.put("mensaje", ex.getMessage());
        return error;
    }

    // NUEVO: Manejo de la Regla de Negocio (HTTP 409 Conflict)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Conflicto de Regla de Negocio (409 Conflict): {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", "Conflicto de Regla de Negocio");
        error.put("mensaje", ex.getMessage());
        return error;
    }
}