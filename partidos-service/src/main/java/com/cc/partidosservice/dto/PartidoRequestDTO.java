package com.cc.partidosservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia (DTO) utilizado para recibir datos de creación o actualización.
 * Incluye Bean Validation para proteger la integridad de los datos (IE 2.2.2).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidoRequestDTO {

    @NotBlank(message = "El nombre del rival es obligatorio")
    private String rival;

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotBlank(message = "El estadio es obligatorio")
    private String estadio;

    // NUEVO: Validamos que siempre se envíe a qué torneo pertenece el partido.
    @NotNull(message = "El ID del torneo es obligatorio")
    private Long torneoId;
}