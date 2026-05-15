package com.cc.usuariosservice.service;

import com.cc.usuariosservice.dto.UsuarioRequestDTO;
import com.cc.usuariosservice.dto.UsuarioResponseDTO;
import com.cc.usuariosservice.entity.Usuario;
import com.cc.usuariosservice.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        log.info("Iniciando registro de usuario con correo: {}", dto.getCorreo());

        // Regla 1: Validar correo unico
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            log.error("Intento duplicado: El correo {} ya se encuentra registrado", dto.getCorreo());
            throw new IllegalArgumentException("Error de negocio: El correo ya esta registrado");
        }

        // Regla 2: Validar rol
        String rol = dto.getRol().toUpperCase();
        if (!rol.equals("ADMIN") && !rol.equals("DT") && !rol.equals("MEDICO")) {
            log.error("Intento invalido: Rol no permitido ({})", rol);
            throw new IllegalArgumentException("Error de negocio: El rol ingresado es invalido. Solo se permite: ADMIN, DT, MEDICO");
        }

        // Regla 3: Validar estado
        String estado = dto.getEstado().toUpperCase();
        if (!estado.equals("ACTIVO") && !estado.equals("INACTIVO")) {
            log.error("Intento invalido: Estado no permitido ({})", estado);
            throw new IllegalArgumentException("Error de negocio: El estado ingresado es invalido. Solo se permite: ACTIVO, INACTIVO");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(rol);
        usuario.setEstado(estado);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("Usuario registrado con exito. ID: {}", usuarioGuardado.getId());

        return mapearADTO(usuarioGuardado);
    }

    public List<UsuarioResponseDTO> obtenerTodos() {
        log.info("Consultando todos los usuarios registrados en el sistema");
        return usuarioRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obtenerPorId(Long id) {
        log.info("Consultando usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error de negocio: El usuario con el ID proporcionado no existe"));
        return mapearADTO(usuario);
    }

    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        log.info("Iniciando actualizacion del usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error de negocio: El usuario con el ID proporcionado no existe"));

        if (!usuario.getCorreo().equals(dto.getCorreo()) && usuarioRepository.existsByCorreo(dto.getCorreo())) {
            log.error("Intento duplicado: El nuevo correo {} ya se encuentra registrado", dto.getCorreo());
            throw new IllegalArgumentException("Error de negocio: El correo ya esta registrado");
        }

        String rol = dto.getRol().toUpperCase();
        if (!rol.equals("ADMIN") && !rol.equals("DT") && !rol.equals("MEDICO")) {
            log.error("Intento invalido: Rol no permitido ({})", rol);
            throw new IllegalArgumentException("Error de negocio: El rol ingresado es invalido. Solo se permite: ADMIN, DT, MEDICO");
        }

        String estado = dto.getEstado().toUpperCase();
        if (!estado.equals("ACTIVO") && !estado.equals("INACTIVO")) {
            log.error("Intento invalido: Estado no permitido ({})", estado);
            throw new IllegalArgumentException("Error de negocio: El estado ingresado es invalido. Solo se permite: ACTIVO, INACTIVO");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(rol);
        usuario.setEstado(estado);

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario con ID {} actualizado exitosamente", id);

        return mapearADTO(actualizado);
    }

    public void eliminarUsuario(Long id) {
        log.info("Iniciando eliminacion del usuario con ID: {}", id);

        if (!usuarioRepository.existsById(id)) {
            log.error("Intento fallido: El usuario con ID {} no existe en el sistema", id);
            throw new IllegalArgumentException("No se puede eliminar: El usuario con el ID especificado no existe");
        }

        usuarioRepository.deleteById(id);
        log.info("Usuario con ID {} eliminado exitosamente", id);
    }

    private UsuarioResponseDTO mapearADTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());
        dto.setEstado(usuario.getEstado());
        return dto;
    }
}