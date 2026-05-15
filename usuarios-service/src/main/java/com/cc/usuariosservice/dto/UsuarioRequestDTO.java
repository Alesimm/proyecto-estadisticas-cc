package com.cc.usuariosservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;

    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "Formato de correo invalido")
    private String correo;

    @NotBlank(message = "La contrasena no puede estar vacia")
    @Size(min = 6, message = "La contrasena debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El rol no puede estar vacio")
    private String rol;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;
}