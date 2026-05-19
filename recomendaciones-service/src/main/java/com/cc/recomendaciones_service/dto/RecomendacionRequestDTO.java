package com.cc.recomendaciones_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecomendacionRequestDTO {
    @NotNull(message = "El idJugador es obligatorio para analizarlo")
    private Long idJugador;
}