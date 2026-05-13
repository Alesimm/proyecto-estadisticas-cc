package com.cc.lesiones_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LesionRequestDTO {

    @NotNull(message = "El idJugador es obligatorio")
    private Long idJugador;

    @NotBlank(message = "El tipo de lesion es obligatorio")
    private String tipoLesion;

    @NotNull(message = "El grado de gravedad es obligatorio")
    @Min(value = 1, message = "El grado minimo es 1")
    @Max(value = 5, message = "El grado maximo es 5")
    private Integer gradoGravedad;

    @NotBlank(message = "La fecha de lesion es obligatoria")
    private String fechaLesion;

    @NotNull(message = "Los dias de recuperacion son obligatorios")
    @Min(value = 0, message = "Los dias de recuperacion no pueden ser negativos")
    private Integer diasRecuperacion;

    @NotBlank(message = "El estado medico es obligatorio")
    private String estadoMedico;
}