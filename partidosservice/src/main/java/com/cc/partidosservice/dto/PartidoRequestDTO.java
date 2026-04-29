package com.cc.partidosservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}