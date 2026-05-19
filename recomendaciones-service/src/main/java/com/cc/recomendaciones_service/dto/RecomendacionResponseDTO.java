package com.cc.recomendaciones_service.dto;

import lombok.Data;

@Data
public class RecomendacionResponseDTO {
    private Long idJugador;
    private String nombreJugador;
    private Double notaRendimiento;
    private Integer minutosAcumulados;
    private String sugerenciaTactica;
    private String prioridad;
}