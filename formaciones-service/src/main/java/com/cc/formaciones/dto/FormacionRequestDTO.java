package com.cc.formaciones.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormacionRequestDTO {

    @NotBlank(message = "El esquema tactico es obligatorio")
    private String esquemaTactico;

    @NotBlank(message = "El estilo de juego es obligatorio")
    private String estiloJuego;

    @NotBlank(message = "La mentalidad es obligatoria")
    private String mentalidad;

    @NotNull(message = "La presion es obligatoria")
    @Min(value = 1, message = "La presion minima es 1")
    @Max(value = 10, message = "La presion maxima es 10")
    private Integer presion;

    @NotNull(message = "La linea defensiva es obligatoria")
    @Min(value = 1, message = "La linea defensiva minima es 1")
    @Max(value = 10, message = "La linea defensiva maxima es 10")
    private Integer lineaDefensiva;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}