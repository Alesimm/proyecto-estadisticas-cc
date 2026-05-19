package com.cc.partidos_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartidoRequestDTO {

    @NotBlank(message = "El rival es obligatorio")
    private String rival;

    @NotBlank(message = "El torneo es obligatorio")
    private String torneo;

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotNull(message = "Los goles de Colo-Colo son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesColoColo;

    @NotNull(message = "Los goles del rival son obligatorios")
    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesRival;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}