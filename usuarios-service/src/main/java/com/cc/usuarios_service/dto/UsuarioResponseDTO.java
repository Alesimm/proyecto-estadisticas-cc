package com.cc.usuarios_service.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String rol;
    private String estado;
}