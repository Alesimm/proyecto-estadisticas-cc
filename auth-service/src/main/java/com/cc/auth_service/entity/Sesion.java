package com.cc.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sesiones")
@Data
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUsuario;
    private String correo;
    private String token;
    private String rol;
    private String fechaCreacion;
    private String estado;
}