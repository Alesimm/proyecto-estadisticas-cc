package com.cc.formaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "esquema_tactico", nullable = false)
    private String esquemaTactico;

    @Column(name = "estilo_juego", nullable = false)
    private String estiloJuego;

    @Column(nullable = false)
    private String mentalidad;

    @Column(nullable = false)
    private Integer presion;

    @Column(name = "linea_defensiva", nullable = false)
    private Integer lineaDefensiva;

    @Column(nullable = false)
    private String estado;
}