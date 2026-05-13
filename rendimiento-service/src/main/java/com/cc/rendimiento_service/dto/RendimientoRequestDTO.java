package com.cc.rendimiento_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RendimientoRequestDTO {

    // Solo pedimos el ID, el resto lo investiga el sistema solo
    @NotNull(message = "El id del jugador es obligatorio para iniciar el analisis")
    private Long idJugador;
}