package com.cc.estadisticas_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadisticaResponseDTO {
    private Long id;
    private Long idJugador;
    private Integer minutosJugados;
    private Integer golesTotales;
    private Integer asistencias;
    private Integer recuperaciones;
    private Integer golesRecibidos;
}