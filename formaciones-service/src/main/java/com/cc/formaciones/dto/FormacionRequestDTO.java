package com.cc.formaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormacionRequestDTO {

    @NotNull(message = "El ID del partido es obligatorio")
    private Long partidoId;

    @NotNull(message = "El ID del equipo es obligatorio")
    private Long equipoId;

    @NotBlank(message = "El esquema táctico es obligatorio (Ej: 4-3-3)")
    private String esquemaTactico;

    @NotBlank(message = "El nombre del director técnico es obligatorio")
    private String directorTecnico;
}