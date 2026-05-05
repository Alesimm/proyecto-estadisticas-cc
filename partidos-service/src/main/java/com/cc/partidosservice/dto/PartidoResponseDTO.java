package com.cc.partidosservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia (DTO) utilizado para devolver datos al cliente,
 * ocultando la estructura real de la entidad Partido y Torneo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidoResponseDTO {
    private Long id;
    private String rival;
    private String fecha;
    private String estadio;

    // NUEVO: Exponemos solo el ID del torneo para mantener la respuesta ligera.
    private Long torneoId;
}