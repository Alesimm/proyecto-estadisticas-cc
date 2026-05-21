package com.cc.reportes_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReporteResponseDTO {

    private Long id;
    private String autorReporte;
    private String tipoReporte;
    private String fechaGeneracion;
    private Integer totalPlantel;
    private Integer jugadoresLesionados;
    private Double promedioEquipo;

}
