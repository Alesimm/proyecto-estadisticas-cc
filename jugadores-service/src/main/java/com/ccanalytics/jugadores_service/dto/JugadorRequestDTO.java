package com.ccanalytics.jugadores_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JugadorRequestDTO {
    @NotBlank(message = "El nombre es obligatorio y no puede estar vacío.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    private String apellido;

    @NotBlank(message = "La posición del jugador es obligatoria.")
    private String posicion;

    @NotNull(message = "El número de camiseta es obligatorio.")
    @Min(value = 1, message = "El número de camiseta debe ser mayor a 0.")
    private Integer numeroCamiseta;
}
