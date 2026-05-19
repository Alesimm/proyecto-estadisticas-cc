package com.cc.usuarios_service.service;

import com.cc.usuarios_service.dto.UsuarioRequestDTO;
import com.cc.usuarios_service.dto.UsuarioResponseDTO;
import com.cc.usuarios_service.entity.Usuario;
import com.cc.usuarios_service.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> obtenerTodos() {
        log.info("Buscando listado completo de usuarios en el sistema central");
        return usuarioRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO registrar(UsuarioRequestDTO request) {
        log.info("Procesando registro de nuevo usuario: {}", request.getCorreo());

        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            log.warn("Registro rechazado: El correo {} ya existe", request.getCorreo());
            throw new IllegalArgumentException("El correo ingresado ya se encuentra registrado.");
        }

        String rolUpper = request.getRol().toUpperCase();
        if (!rolUpper.equals("ADMIN") && !rolUpper.equals("DT") && !rolUpper.equals("MEDICO")) {
            throw new IllegalArgumentException("Rol no permitido. Valores validos: ADMIN, DT, MEDICO");
        }

        String estadoUpper = request.getEstado().toUpperCase();
        if (!estadoUpper.equals("ACTIVO") && !estadoUpper.equals("INACTIVO")) {
            throw new IllegalArgumentException("Estado no permitido. Valores validos: ACTIVO, INACTIVO");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setApellido(request.getApellido());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setContrasena(request.getContrasena());
        nuevo.setRol(rolUpper);
        nuevo.setEstado(estadoUpper);

        return mapearADTO(usuarioRepository.save(nuevo));
    }

    private UsuarioResponseDTO mapearADTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setContrasena(usuario.getContrasena());
        dto.setRol(usuario.getRol());
        dto.setEstado(usuario.getEstado());
        return dto;
    }
}