package com.cc.rendimiento_service.dto;

import lombok.Data;

@Data
public class RendimientoResponseDTO {
    private Long id;
    private Long idJugador;
    private String posicion;
    private Integer minutosJugados;
    private Integer golesImpacto;
    private Integer recuperaciones;
    private Double notaFinal;
}