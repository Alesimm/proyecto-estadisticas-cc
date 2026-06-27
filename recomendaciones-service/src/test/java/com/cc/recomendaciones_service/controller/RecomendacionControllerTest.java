package com.cc.recomendaciones_service.controller;

import com.cc.recomendaciones_service.dto.*;
import com.cc.recomendaciones_service.service.RecomendacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecomendacionController.class)
class RecomendacionControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private RecomendacionService service;
    @Autowired private ObjectMapper mapper;

    @Test
    void testAnalizar_Ok() throws Exception {
        when(service.generarAnalisis(any())).thenReturn(new RecomendacionResponseDTO());
        mockMvc.perform(post("/api/recomendaciones/analizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idJugador\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testAnalizar_Error500() throws Exception {
        when(service.generarAnalisis(any())).thenThrow(new RuntimeException("Error"));
        mockMvc.perform(post("/api/recomendaciones/analizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idJugador\":1}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testListar_Ok() throws Exception {
        mockMvc.perform(get("/api/recomendaciones")).andExpect(status().isOk());
    }
}