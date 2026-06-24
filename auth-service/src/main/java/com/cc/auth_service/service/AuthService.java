package com.cc.auth_service.service;

import com.cc.auth_service.client.UsuarioClient;
import com.cc.auth_service.dto.AuthRequestDTO;
import com.cc.auth_service.dto.AuthResponseDTO;
import com.cc.auth_service.entity.Sesion;
import com.cc.auth_service.repository.SesionRepository;
import com.cc.auth_service.security.JwtUtil; // Importamos la nueva herramienta
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private SesionRepository sesionRepository;

    public AuthResponseDTO login(AuthRequestDTO request) {
        log.info("Intento de login para el correo: {}", request.getCorreo());

        // ir usuarios buscar informacion
        Map<String, Object> usuarioData = usuarioClient.buscarUsuarioPorCorreo(request.getCorreo());

        // validar estado
        Object estadoObj = usuarioData.get("estado");
        if (estadoObj == null || !estadoObj.toString().equalsIgnoreCase("ACTIVO")) {
            log.warn("Login rechazado: Usuario inactivo");
            throw new IllegalArgumentException("El usuario se encuentra inactivo y no puede iniciar sesion.");
        }

        // validar contrsñ
        Object passwordObj = usuarioData.get("contrasena");
        if (passwordObj == null || !passwordObj.toString().equals(request.getContrasena())) {
            throw new IllegalArgumentException("Credenciales invalidas (Contrasena incorrecta).");
        }

        // extrae datos antes de generar el token
        Long idUsuario = Long.valueOf(usuarioData.get("id").toString());
        String rol = usuarioData.get("rol").toString();

        // generar JWT
        String tokenGenerado = JwtUtil.generarToken(request.getCorreo(), rol);

        Sesion sesion = new Sesion();
        sesion.setIdUsuario(idUsuario);
        sesion.setCorreo(request.getCorreo());
        sesion.setToken(tokenGenerado); // guardamos el jwt largo en la base de datos (como historial)
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