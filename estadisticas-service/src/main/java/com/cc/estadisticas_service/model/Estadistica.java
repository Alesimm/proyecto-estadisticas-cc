package com.cc.estadisticas_service.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "jugador_id", nullable = false)
    private Long jugadorId;

    @Column(name = "partido_id", nullable = false)
    private Long partidoId;

    private Integer goles;
    private Integer asistencias;
    private Integer intercepciones;
    private Integer recuperaciones;
    private Integer atajadas;
}