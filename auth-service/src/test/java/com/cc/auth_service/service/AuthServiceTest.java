package com.cc.auth_service.service;

import com.cc.auth_service.client.UsuarioClient;
import com.cc.auth_service.dto.*;
import com.cc.auth_service.entity.Sesion;
import com.cc.auth_service.repository.SesionRepository;
import com.cc.auth_service.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UsuarioClient usuarioClient;
    @Mock private SesionRepository sesionRepository;
    @InjectMocks private AuthService authService;

    @Test
    void test_login_ok() {
        Map<String, Object> userData = Map.of("id", "1", "estado", "ACTIVO", "contrasena", "123", "rol", "ADMIN");
        when(usuarioClient.buscarUsuarioPorCorreo(anyString())).thenReturn(userData);

        try (MockedStatic<JwtUtil> jwtMock = mockStatic(JwtUtil.class)) {
            jwtMock.when(() -> JwtUtil.generarToken(anyString(), anyString())).thenReturn("token-falso");

            AuthRequestDTO req = new AuthRequestDTO();
            req.setCorreo("cris@cc.cl");
            req.setContrasena("123");

            AuthResponseDTO res = authService.login(req);
            assertThat(res.getToken()).isEqualTo("token-falso");
            verify(sesionRepository, times(1)).save(any(Sesion.class));
        }
    }

    @Test
    void test_login_inactivo() {
        when(usuarioClient.buscarUsuarioPorCorreo(anyString())).thenReturn(Map.of("estado", "INACTIVO"));
        AuthRequestDTO req = new AuthRequestDTO();
        req.setCorreo("cris@cc.cl");

        assertThatThrownBy(() -> authService.login(req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void test_login_fallo_critico_db() {
        when(usuarioClient.buscarUsuarioPorCorreo(anyString())).thenReturn(Map.of("id", "1", "estado", "ACTIVO", "contrasena", "123", "rol", "ADMIN"));
        // Simulamos que la base de datos falla al guardar
        when(sesionRepository.save(any(Sesion.class))).thenThrow(new RuntimeException("DB falló"));

        try (MockedStatic<JwtUtil> jwtMock = mockStatic(JwtUtil.class)) {
            jwtMock.when(() -> JwtUtil.generarToken(anyString(), anyString())).thenReturn("token");

            AuthRequestDTO req = new AuthRequestDTO();
            req.setCorreo("cris@cc.cl");
            req.setContrasena("123");

            assertThatThrownBy(() -> authService.login(req)).isInstanceOf(RuntimeException.class);
        }
    }
}