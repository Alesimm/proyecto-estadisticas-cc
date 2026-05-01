package com.cc.formaciones.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormacionDTO {
    private Long id;

    @NotBlank(message = "El nombre de la formación no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "La cantidad de defensas es obligatoria")
    @Min(value = 0, message = "Los defensas no pueden ser negativos")
    private Integer defensas;

    @NotNull(message = "La cantidad de mediocampistas es obligatoria")
    @Min(value = 0, message = "Los mediocampistas no pueden ser negativos")
    private Integer mediocampistas;

    @NotNull(message = "La cantidad de delanteros es obligatoria")
    @Min(value = 0, message = "Los delanteros no pueden ser negativos")
    private Integer delanteros;
}