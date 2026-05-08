package com.cc.partidosservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad principal que mapea la tabla 'partidos' en la base de datos MySQL.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rival;
    private String fecha;
    private String estadio;

    /**
     * RELACIÓN: Un torneo puede tener muchos partidos, pero un partido pertenece a un solo torneo.
     * Esta relación asegura la Integridad Referencial requerida en la pauta (IE 2.2.3).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "torneo_id", nullable = false)
    private Torneo torneo;
}