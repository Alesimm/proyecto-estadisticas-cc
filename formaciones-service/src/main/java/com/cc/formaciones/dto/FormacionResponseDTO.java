package com.cc.formaciones.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormacionResponseDTO {
    private Long id;
    private String esquemaTactico;
    private String estiloJuego;
    private String mentalidad;
    private Integer presion;
    private Integer lineaDefensiva;
    private String estado;
}