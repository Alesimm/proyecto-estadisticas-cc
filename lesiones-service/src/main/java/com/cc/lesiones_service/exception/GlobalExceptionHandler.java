package com.cc.lesiones_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidations(MethodArgumentNotValidException problemita) {
        log.error("Fallo en la validacion de campos DTO");
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : problemita.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleLogicaNegocio(IllegalArgumentException problemita) {
        log.error("Fallo en la logica de negocio: {}", problemita.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", problemita.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenerico(Exception problemita) {
        log.error("Error no controlado en el servidor: {}", problemita.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrio un fallo interno inesperado");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}