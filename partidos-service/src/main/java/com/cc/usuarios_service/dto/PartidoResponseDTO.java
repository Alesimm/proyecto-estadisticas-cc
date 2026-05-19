package com.cc.usuarios_service.dto;

import lombok.Data;

@Data
public class PartidoResponseDTO {
    private Long id;
    private String rival;
    private String torneo;
    private String fecha;
    private Integer golesColoColo;
    private Integer golesRival;
    private String estado;
}