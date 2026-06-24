package com.cc.estadisticas_service.controller;

import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.service.EstadisticaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstadisticaController.class)
class EstadisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadisticaService estadisticaService;

    @Test
    void listarEstadisticas_deberiaRetornarLista() throws Exception {
        EstadisticaResponseDTO dto = EstadisticaResponseDTO.builder()
                .id(1L)
                .idJugador(10L)
                .minutosJugados(90)
                .golesTotales(1)
                .asistencias(0)
                .recuperaciones(5)
                .golesRecibidos(0)
                .build();

        when(estadisticaService.listar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/estadisticas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].idJugador").value(10))
                .andExpect(jsonPath("$[0].minutosJugados").value(90));
    }

    @Test
    void crearEstadistica_cuandoJsonEsValido_deberiaRetornarCreated() throws Exception {
        EstadisticaResponseDTO respuesta = EstadisticaResponseDTO.builder()
                .id(2L)
                .idJugador(15L)
                .minutosJugados(45)
                .golesTotales(0)
                .asistencias(0)
                .recuperaciones(2)
                .golesRecibidos(0)
                .build();

        when(estadisticaService.crear(any(EstadisticaRequestDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/estadisticas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idJugador": 15,
                                  "minutosJugados": 45,
                                  "golesTotales": 0,
                                  "asistencias": 0,
                                  "recuperaciones": 2,
                                  "golesRecibidos": 0
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.idJugador").value(15));
    }

    @Test
    void crearEstadistica_cuandoJsonEsInvalido_deberiaRetornarBadRequest() throws Exception {
        mockMvc.perform(post("/api/estadisticas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idJugador": null,
                                  "minutosJugados": -10,
                                  "golesTotales": 0,
                                  "asistencias": 0,
                                  "recuperaciones": 0,
                                  "golesRecibidos": 0
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(estadisticaService, never()).crear(any());
    }
}