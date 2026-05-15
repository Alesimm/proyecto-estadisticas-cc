package com.cc.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {
    private String token;
    private String correo;
    private String rol;
    private String mensaje;
}