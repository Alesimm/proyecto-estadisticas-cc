package com.cc.estadisticas_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadisticaRequestDTO {
    @NotNull(message = "El ID del jugador es obligatorio")
    private Long jugadorId;
    @NotNull(message = "El ID del partido es obligatorio")
    private Long partidoId;
    @Min(0) private Integer goles;
    @Min(0) private Integer asistencias;
    @Min(0) private Integer intercepciones;
    @Min(0) private Integer recuperaciones;
    @Min(0) private Integer atajadas;
}