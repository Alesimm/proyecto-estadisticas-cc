package com.cc.reportes_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReporteRequestDTO {

    @NotBlank(message = "El autor del reporte es obligatorio")
    private String autorReporte;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String tipoReporte;

}
