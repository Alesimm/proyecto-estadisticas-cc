package com.cc.recomendacionesservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecomendacionRequestDTO {
    @NotNull(message = "El idJugador es obligatorio")
    private Long idJugador;
}