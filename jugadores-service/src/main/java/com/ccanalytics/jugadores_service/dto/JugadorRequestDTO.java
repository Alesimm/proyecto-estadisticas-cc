package com.ccanalytics.jugadores_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JugadorRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "La posicion es obligatoria")
    private String posicion;

    @NotNull(message = "El numero de camiseta es obligatorio")
    @Min(value = 1, message = "El numero de camiseta no puede ser menor a 1")
    @Max(value = 99, message = "El numero de camiseta no puede ser mayor a 99")
    private Integer numeroCamiseta;

    @NotBlank(message = "La nacionalidad es obligatoria")
    private String nacionalidad;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 15, message = "El jugador debe tener al menos 15 anios")
    private Integer edad;

    @NotBlank(message = "El correo de contacto es obligatorio")
    @Email(message = "Debes ingresar un correo valido")
    private String correoContacto;
}