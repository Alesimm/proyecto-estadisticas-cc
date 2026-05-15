package com.cc.recomendacionesservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecomendacionResponseDTO {
    private Long id;
    private Long idJugador;
    private String nombreJugador;
    private Double notaRendimiento;
    private Integer minutosAcumulados;
    private String sugerenciaTactica;
    private String prioridad;
}