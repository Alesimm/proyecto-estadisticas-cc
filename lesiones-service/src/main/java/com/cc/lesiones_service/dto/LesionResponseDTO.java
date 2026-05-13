package com.cc.lesiones_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LesionResponseDTO {
    private Long id;
    private Long idJugador;
    private String tipoLesion;
    private Integer gradoGravedad;
    private String fechaLesion;
    private Integer diasRecuperacion;
    private String estadoMedico;
}