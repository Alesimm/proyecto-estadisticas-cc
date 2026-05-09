package com.cc.estadisticas_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadisticaResponseDTO {
    private Long id;
    private Long jugadorId;
    private Long partidoId;
    private Integer goles;
    private Integer asistencias;
    private Integer intercepciones;
    private Integer recuperaciones;
    private Integer atajadas;
    private String mensaje;
}