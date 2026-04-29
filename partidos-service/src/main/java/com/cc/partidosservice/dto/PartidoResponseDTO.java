package com.cc.partidosservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidoResponseDTO {
    private Long id;
    private String rival;
    private String fecha;
    private String estadio;
}