package com.cc.lesiones_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lesiones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_jugador", nullable = false)
    private Long idJugador;

    @Column(name = "tipo_lesion", nullable = false)
    private String tipoLesion;

    @Column(name = "grado_gravedad", nullable = false)
    private Integer gradoGravedad;

    @Column(name = "fecha_lesion", nullable = false)
    private String fechaLesion;

    @Column(name = "dias_recuperacion", nullable = false)
    private Integer diasRecuperacion;

    @Column(name = "estado_medico", nullable = false)
    private String estadoMedico;
}