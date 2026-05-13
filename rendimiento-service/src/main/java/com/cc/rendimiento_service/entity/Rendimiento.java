package com.cc.rendimiento_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rendimientos")
@Data
public class Rendimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_jugador", unique = true)
    private Long idJugador;

    private String posicion;
    private Integer minutosJugados;
    private Integer golesImpacto;
    private Integer recuperaciones;
    private Double notaFinal;
}