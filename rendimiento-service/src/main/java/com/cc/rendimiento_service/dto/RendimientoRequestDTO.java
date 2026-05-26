package com.cc.rendimiento_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RendimientoRequestDTO {

    @NotNull(message = "El id del jugador es obligatorio para iniciar el analisis")
    private Long idJugador;
}