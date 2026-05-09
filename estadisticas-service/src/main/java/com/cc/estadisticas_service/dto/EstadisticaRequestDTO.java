package com.cc.estadisticas_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadisticaRequestDTO {
<<<<<<< HEAD

    @NotNull(message = "El ID del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "El ID del partido es obligatorio")
    private Long partidoId;

    @NotNull(message = "Los goles son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer goles;

    @NotNull(message = "Las asistencias son obligatorias")
    @Min(value = 0, message = "Las asistencias no pueden ser negativas")
    private Integer asistencias;

    @NotNull(message = "Las intercepciones son obligatorias")
    @Min(value = 0, message = "Las intercepciones no pueden ser negativas")
    private Integer intercepciones;

    @NotNull(message = "Las recuperaciones son obligatorias")
    @Min(value = 0, message = "Las recuperaciones no pueden ser negativas")
    private Integer recuperaciones;

    @NotNull(message = "Las atajadas son obligatorias")
    @Min(value = 0, message = "Las atajadas no pueden ser negativas")
    private Integer atajadas;
=======
    @NotNull(message = "El ID del jugador es obligatorio")
    private Long jugadorId;
    @NotNull(message = "El ID del partido es obligatorio")
    private Long partidoId;
    @Min(0) private Integer goles;
    @Min(0) private Integer asistencias;
    @Min(0) private Integer intercepciones;
    @Min(0) private Integer recuperaciones;
    @Min(0) private Integer atajadas;
>>>>>>> main
}