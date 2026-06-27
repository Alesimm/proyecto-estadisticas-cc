package com.cc.rendimiento_service.controller;

import com.cc.rendimiento_service.dto.RendimientoRequestDTO;
import com.cc.rendimiento_service.dto.RendimientoResponseDTO;
import com.cc.rendimiento_service.service.RendimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RendimientoController.class)
class RendimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RendimientoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private RendimientoRequestDTO request;
    private RendimientoResponseDTO response;

    @BeforeEach
    void setUp() {
        request = new RendimientoRequestDTO();
        request.setIdJugador(1L);

        response = new RendimientoResponseDTO();
        response.setId(1L);
        response.setIdJugador(1L);
        response.setPosicion("Delantero");
        response.setNotaFinal(5.5);
    }

    // CASO 1: POST Calcular exitoso - Retorna 201
    @Test
    void calcular_cuandoPeticionEsValida_retorna201Created() throws Exception {
        when(service.calcular(any(RendimientoRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/rendimientos/calcular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notaFinal").value(5.5));
    }

    // CASO 2: POST Calcular falla por API remota - Retorna Error
    @Test
    void calcular_cuandoFallaServicioRemoto_retorna400BadRequest() throws Exception {
        when(service.calcular(any(RendimientoRequestDTO.class)))
                .thenThrow(new RuntimeException("Error API externa"));

        mockMvc.perform(post("/api/rendimientos/calcular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // CASO 3: GET Listar exitoso - Retorna 200
    @Test
    void listar_cuandoExistenRegistros_retorna200Ok() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/rendimientos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].posicion").value("Delantero"));
    }
}