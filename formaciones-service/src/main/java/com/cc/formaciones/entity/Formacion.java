package com.cc.formaciones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "formaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Formacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    private int defensas;
    private int mediocampistas;
    private int delanteros;

}