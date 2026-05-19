package com.cc.usuarios_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ingresar un formato de correo valido")
    private String correo;

    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 6, message = "La contrasena debe tener un minimo de 6 caracteres")
    private String contrasena;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}