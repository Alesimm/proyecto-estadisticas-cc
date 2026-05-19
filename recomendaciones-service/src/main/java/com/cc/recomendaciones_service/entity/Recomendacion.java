package com.cc.recomendaciones_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "recomendaciones")
@Data
public class Recomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_jugador", unique = true)
    private Long idJugador;

    private String nombreJugador;
    private Double notaRendimiento;
    private Integer minutosAcumulados;
    private String sugerenciaTactica;
    private String prioridad;
}