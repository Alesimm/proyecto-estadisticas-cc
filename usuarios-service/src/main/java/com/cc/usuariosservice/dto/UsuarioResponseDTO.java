package com.cc.usuariosservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String password; // <-- AGREGAMOS ESTO
    private String rol;
    private String estado;
}