package com.cc.estadisticas_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estadisticas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estadistica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_jugador", unique = true, nullable = false)
    private Long idJugador;

    @Column(name = "minutos_jugados", nullable = false)
    private Integer minutosJugados;

    @Column(name = "goles_totales", nullable = false)
    private Integer golesTotales;

    @Column(name = "asistencias", nullable = false)
    private Integer asistencias;

    @Column(name = "recuperaciones", nullable = false)
    private Integer recuperaciones;

    @Column(name = "goles_recibidos", nullable = false)
    private Integer golesRecibidos;
}