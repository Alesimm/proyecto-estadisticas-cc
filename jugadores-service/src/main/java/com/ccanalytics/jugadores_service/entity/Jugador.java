package com.ccanalytics.jugadores_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "jugadores")
@Data
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String posicion;

    @Column(name = "numero_camiseta", unique = true)
    private Integer numeroCamiseta;

    private String nacionalidad;
    private Integer edad;
}