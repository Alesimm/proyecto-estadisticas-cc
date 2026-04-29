package com.ccanalytics.jugadores_service.dto;

import lombok.Data;

@Data
public class JugadorResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String posicion;
    private Integer numeroCamiseta;
}