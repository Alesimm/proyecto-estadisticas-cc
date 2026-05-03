package com.cc.formaciones.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formaciones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Formacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partido_id", nullable = false)
    private Long partidoId;

    @Column(name = "equipo_id", nullable = false)
    private Long equipoId;

    @Column(name = "esquema_tactico", nullable = false, length = 15)
    private String esquemaTactico;

    @Column(name = "director_tecnico", nullable = false, length = 100)
    private String directorTecnico;
}