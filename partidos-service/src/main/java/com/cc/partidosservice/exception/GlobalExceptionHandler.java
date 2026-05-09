package com.cc.partidosservice.exception;

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
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException problemita) {
        log.warn("Se detectaron errores de validacion en la estructura del JSON");
        Map<String, String> errores = new HashMap<>();
        problemita.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBusinessExceptions(IllegalArgumentException problemita) {
        log.warn("Se rechazo la peticion por regla de negocio: {}", problemita.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", problemita.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleMalformedJson(org.springframework.http.converter.HttpMessageNotReadableException problemita) {
        log.warn("Se recibio un JSON mal formado o con sintaxis incorrecta");
        Map<String, String> error = new HashMap<>();
        error.put("error", "El formato del JSON enviado es incorrecto o contiene un error de sintaxis");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}