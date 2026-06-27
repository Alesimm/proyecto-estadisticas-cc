package com.cc.reportes_service.controller;

import com.cc.reportes_service.dto.*;
import com.cc.reportes_service.service.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
// ESTOS SON LOS IMPORTS QUE TE ESTABAN DANDO ERROR, AHORA ESTÁN CORRECTOS:
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ReporteService service;
    @Autowired private ObjectMapper mapper;

    @Test
    void test_crear_reporte_ok() throws Exception {
        // Mock del servicio
        when(service.generarReporte(any())).thenReturn(ReporteResponseDTO.builder().id(1L).build());

        // JSON con todos los campos requeridos para evitar el error 400 de validación
        String json = "{\"autorReporte\":\"cris\", \"tipoReporte\":\"Mensual\"}";

        mockMvc.perform(post("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void test_crear_reporte_error_500() throws Exception {
        when(service.generarReporte(any())).thenThrow(new RuntimeException("error"));

        String json = "{\"autorReporte\":\"cris\", \"tipoReporte\":\"Mensual\"}";

        mockMvc.perform(post("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void test_listar_reportes_ok() throws Exception {
        mockMvc.perform(get("/api/reportes")).andExpect(status().isOk());
    }
}