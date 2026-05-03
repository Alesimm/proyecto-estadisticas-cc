package com.cc.formaciones.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormacionResponseDTO {
    private Long id;
    private Long partidoId;
    private Long equipoId;
    private String esquemaTactico;
    private String directorTecnico;
    private String estado;
}