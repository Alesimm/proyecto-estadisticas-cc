package com.cc.usuarios_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "partido")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rival;
    private String torneo;
    private String fecha;
    private Integer golesColoColo;
    private Integer golesRival;
    private String estado;
}