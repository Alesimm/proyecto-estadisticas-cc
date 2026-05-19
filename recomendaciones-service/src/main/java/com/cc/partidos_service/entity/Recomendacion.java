package com.cc.partidos_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recomendacion")
@Getter
@Setter
public class Recomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_jugador", nullable = false, unique = true)
    private Long idJugador;

    @Column(name = "nombre_jugador", nullable = false)
    private String nombreJugador;

    @Column(name = "nota_rendimiento", nullable = false)
    private Double notaRendimiento;

    @Column(name = "minutos_acumulados", nullable = false)
    private Integer minutosAcumulados;

    @Column(name = "sugerencia_tactica", nullable = false)
    private String sugerenciaTactica;

    @Column(name = "prioridad", nullable = false)
    private String prioridad;
}