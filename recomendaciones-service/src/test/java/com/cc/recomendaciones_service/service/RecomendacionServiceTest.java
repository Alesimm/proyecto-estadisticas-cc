package com.cc.recomendaciones_service.service;

import com.cc.recomendaciones_service.client.*;
import com.cc.recomendaciones_service.dto.*;
import com.cc.recomendaciones_service.entity.Recomendacion;
import com.cc.recomendaciones_service.repository.RecomendacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecomendacionServiceTest {

    @Mock private JugadorClient jugadorClient;
    @Mock private EstadisticaClient estadisticaClient;
    @Mock private RendimientoClient rendimientoClient;
    @Mock private RecomendacionRepository repository;

    @InjectMocks private RecomendacionService service;

    // CASO 1: Camino Feliz
    @Test
    void testGenerarAnalisis_Exitoso() {
        when(jugadorClient.obtenerJugador(anyLong())).thenReturn(Map.of("nombre", "Arturo", "apellido", "Vidal"));
        when(estadisticaClient.obtenerEstadisticas(anyLong())).thenReturn(Map.of("minutosJugados", "100"));
        when(rendimientoClient.obtenerNota(anyLong())).thenReturn(Map.of("notaFinal", "6.5"));
        when(repository.findByIdJugador(anyLong())).thenReturn(Optional.empty());
        when(repository.save(any(Recomendacion.class))).thenAnswer(i -> i.getArguments()[0]);

        RecomendacionRequestDTO req = new RecomendacionRequestDTO(); // Usamos constructor vacío
        req.setIdJugador(1L); // Usamos setter

        RecomendacionResponseDTO res = service.generarAnalisis(req);

        assertThat(res).isNotNull();
        assertThat(res.getNombreJugador()).isEqualTo("Arturo Vidal");
    }

    // CASO 2: Error en WebClient (Falla de conexión externa)
    @Test
    void testGenerarAnalisis_ErrorEnClient() {
        when(jugadorClient.obtenerJugador(anyLong())).thenThrow(new RuntimeException("API caida"));

        RecomendacionRequestDTO req = new RecomendacionRequestDTO();
        req.setIdJugador(1L);

        assertThatThrownBy(() -> service.generarAnalisis(req))
                .isInstanceOf(RuntimeException.class);
    }

    // CASO 3: Listar
    @Test
    void testListar() {
        when(repository.findAll()).thenReturn(List.of(new Recomendacion()));
        assertThat(service.listarTodos()).hasSize(1);
    }
}