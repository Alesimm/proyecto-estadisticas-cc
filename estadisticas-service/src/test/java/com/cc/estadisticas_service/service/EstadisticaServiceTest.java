package com.cc.estadisticas_service.service;

import com.cc.estadisticas_service.client.JugadorClient;
import com.cc.estadisticas_service.dto.EstadisticaRequestDTO;
import com.cc.estadisticas_service.dto.EstadisticaResponseDTO;
import com.cc.estadisticas_service.model.Estadistica;
import com.cc.estadisticas_service.repository.EstadisticaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadisticaServiceTest {

    @Mock
    private EstadisticaRepository repository;

    @Mock
    private JugadorClient jugadorClient;

    @InjectMocks
    private EstadisticaService estadisticaService;

    @Test
    void crear_cuandoJugadorExisteYDatosValidos_deberiaGuardarExitosamente() {
        EstadisticaRequestDTO request = new EstadisticaRequestDTO();
        request.setIdJugador(10L);
        request.setMinutosJugados(90);
        request.setGolesTotales(2);
        request.setAsistencias(1);
        request.setRecuperaciones(5);
        request.setGolesRecibidos(0);

        Estadistica estadisticaGuardada = Estadistica.builder()
                .id(1L)
                .idJugador(10L)
                .minutosJugados(90)
                .golesTotales(2)
                .asistencias(1)
                .recuperaciones(5)
                .golesRecibidos(0)
                .build();

        when(repository.existsByIdJugador(10L)).thenReturn(false);
        when(jugadorClient.obtenerJugador(10L)).thenReturn(Map.of("id", 10L));
        when(repository.save(any(Estadistica.class))).thenReturn(estadisticaGuardada);

        EstadisticaResponseDTO respuesta = estadisticaService.crear(request);

        assertThat(respuesta.getId()).isEqualTo(1L);
        assertThat(respuesta.getIdJugador()).isEqualTo(10L);
        assertThat(respuesta.getGolesTotales()).isEqualTo(2);

        ArgumentCaptor<Estadistica> captor = ArgumentCaptor.forClass(Estadistica.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getMinutosJugados()).isEqualTo(90);
    }

    @Test
    void crear_cuandoJugadorNoExisteEnApiExterna_deberiaLanzarExcepcion() {
        EstadisticaRequestDTO request = new EstadisticaRequestDTO();
        request.setIdJugador(99L);
        request.setMinutosJugados(45);
        request.setGolesTotales(0);
        request.setAsistencias(0);
        request.setRecuperaciones(0);
        request.setGolesRecibidos(0);

        when(repository.existsByIdJugador(99L)).thenReturn(false);
        when(jugadorClient.obtenerJugador(99L)).thenThrow(new RuntimeException("Not Found"));

        assertThatThrownBy(() -> estadisticaService.crear(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Error al conectar con la API de Jugadores o el jugador no existe.");

        verify(repository, never()).save(any());
    }

    @Test
    void crear_cuandoDatosInconsistentes_deberiaLanzarExcepcion() {
        EstadisticaRequestDTO request = new EstadisticaRequestDTO();
        request.setIdJugador(10L);
        request.setMinutosJugados(0);
        request.setGolesTotales(1);
        request.setAsistencias(0);
        request.setRecuperaciones(0);
        request.setGolesRecibidos(0);

        when(repository.existsByIdJugador(10L)).thenReturn(false);

        assertThatThrownBy(() -> estadisticaService.crear(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Los minutos jugados no pueden ser 0 si el jugador tiene goles");

        verify(jugadorClient, never()).obtenerJugador(any());
        verify(repository, never()).save(any());
    }
}