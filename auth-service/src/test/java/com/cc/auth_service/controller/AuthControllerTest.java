package com.cc.auth_service.controller;

import com.cc.auth_service.dto.*;
import com.cc.auth_service.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private AuthService authService;
    @Autowired private ObjectMapper mapper;

    @Test
    void test_login_ok() throws Exception {
        when(authService.login(any())).thenReturn(AuthResponseDTO.builder().token("token").build());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"correo\":\"cris@cc.cl\", \"contrasena\":\"123\"}"))
                .andExpect(status().isOk()); // 200 OK
    }

    @Test
    void test_login_invalido() throws Exception {
        // Tu log mostró que el servicio lanza IllegalArgumentException y el handler responde 401
        when(authService.login(any())).thenThrow(new IllegalArgumentException("error"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"correo\":\"cris@cc.cl\", \"contrasena\":\"123\"}"))
                .andExpect(status().isUnauthorized()); // 401 Unauthorized
    }

    @Test
    void test_login_error_500() throws Exception {
        // Tu log mostró que el servicio lanza RuntimeException y el handler responde 500
        when(authService.login(any())).thenThrow(new RuntimeException("error fatal"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"correo\":\"cris@cc.cl\", \"contrasena\":\"123\"}"))
                .andExpect(status().isInternalServerError()); // 500 Internal Server Error
    }
}