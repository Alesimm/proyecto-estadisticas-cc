package com.cc.reportes_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reportes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String autorReporte;
    private String tipoReporte;
    private String fechaGeneracion;
    private Integer totalPlantel;
    private Integer jugadoresLesionados;
    private Double promedioEquipo;
}