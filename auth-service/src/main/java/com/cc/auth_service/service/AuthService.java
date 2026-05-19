package com.cc.auth_service.service;

import com.cc.auth_service.client.UsuarioClient;
import com.cc.auth_service.dto.AuthRequestDTO;
import com.cc.auth_service.dto.AuthResponseDTO;
import com.cc.auth_service.entity.Sesion;
import com.cc.auth_service.repository.SesionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private SesionRepository sesionRepository;

    public AuthResponseDTO login(AuthRequestDTO request) {
        log.info("Intento de login para el correo: {}", request.getCorreo());

        // Ir al servicio de Usuarios a buscar la informacion
        Map<String, Object> usuarioData = usuarioClient.buscarUsuarioPorCorreo(request.getCorreo());

        // Validar Estado
        Object estadoObj = usuarioData.get("estado");
        if (estadoObj == null || !estadoObj.toString().equalsIgnoreCase("ACTIVO")) {
            log.warn("Login rechazado: Usuario inactivo");
            throw new IllegalArgumentException("El usuario se encuentra inactivo y no puede iniciar sesion.");
        }

        // Validar Contrasena REAL
        Object passwordObj = usuarioData.get("contrasena");
        if (passwordObj == null || !passwordObj.toString().equals(request.getContrasena())) {
            throw new IllegalArgumentException("Credenciales invalidas (Contrasena incorrecta).");
        }

        // Generar Token Unico
        String tokenGenerado = UUID.randomUUID().toString();
        Long idUsuario = Long.valueOf(usuarioData.get("id").toString());
        String rol = usuarioData.get("rol").toString();

        Sesion sesion = new Sesion();
        sesion.setIdUsuario(idUsuario);
        sesion.setCorreo(request.getCorreo());
        sesion.setToken(tokenGenerado);
        sesion.setRol(rol);
        sesion.setFechaCreacion(LocalDateTime.now().toString());
        sesion.setEstado("ACTIVA");

        sesionRepository.save(sesion);
        log.info("Login exitoso. Sesion creada para: {}", request.getCorreo());

        return AuthResponseDTO.builder()
                .token(tokenGenerado)
                .correo(request.getCorreo())
                .rol(rol)
                .mensaje("Autenticacion exitosa. Bienvenido al sistema CC Analytics.")
                .build();
    }
}