package com.cc.estadisticas_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadisticaRequestDTO {

    @NotNull(message = "El idJugador es obligatorio")
    private Long idJugador;

    @NotNull(message = "Los minutos jugados son obligatorios")
    @Min(value = 0, message = "El valor no puede ser negativo")
    private Integer minutosJugados;

    @NotNull(message = "Los goles totales son obligatorios")
    @Min(value = 0, message = "El valor no puede ser negativo")
    private Integer golesTotales;

    @NotNull(message = "Las asistencias son obligatorias")
    @Min(value = 0, message = "El valor no puede ser negativo")
    private Integer asistencias;

    @NotNull(message = "Las recuperaciones son obligatorias")
    @Min(value = 0, message = "El valor no puede ser negativo")
    private Integer recuperaciones;

    @NotNull(message = "Los goles recibidos son obligatorios")
    @Min(value = 0, message = "El valor no puede ser negativo")
    private Integer golesRecibidos;
}