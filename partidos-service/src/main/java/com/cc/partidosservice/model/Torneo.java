package com.cc.partidosservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un Torneo en la base de datos.
 * Creada para mantener la base de datos normalizada y permitir relaciones funcionales (IE 2.1.1).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "torneos")
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
}