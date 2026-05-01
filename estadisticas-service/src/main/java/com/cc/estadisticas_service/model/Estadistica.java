package com.cc.estadisticas_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estadisticas")
public class Estadistica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jugadorId;
    private Long partidoId;
    private Integer goles;
    private Integer asistencias;
    private Integer intercepciones;
    private Integer recuperaciones;
    private Integer atajadas;
}