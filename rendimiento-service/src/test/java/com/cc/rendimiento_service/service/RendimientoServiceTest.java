package com.cc.rendimiento_service.service;

import com.cc.rendimiento_service.client.EstadisticaClient;
import com.cc.rendimiento_service.client.JugadorClient;
import com.cc.rendimiento_service.dto.RendimientoRequestDTO;
import com.cc.rendimiento_service.dto.RendimientoResponseDTO;
import com.cc.rendimiento_service.entity.Rendimiento;
import com.cc.rendimiento_service.repository.RendimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RendimientoServiceTest {

    @Mock
    private RendimientoRepository repository;

    @Mock
    private JugadorClient jugadorClient;

    @Mock
    private EstadisticaClient estadisticaClient;

    @InjectMocks
    private RendimientoService service;

    private RendimientoRequestDTO request;
    private Rendimiento rendimiento;
    private Map<String, Integer> statsMock;

    @BeforeEach
    void setUp() {
        request = new RendimientoRequestDTO();
        request.setIdJugador(1L);

        rendimiento = new Rendimiento();
        rendimiento.setId(1L);
        rendimiento.setIdJugador(1L);
        rendimiento.setPosicion("Delantero");
        rendimiento.setNotaFinal(5.5);

        // Mapa exacto que espera tu método obtenerStats()
        statsMock = Map.of(
                "minutosJugados", 90,
                "golesTotales", 2,
                "recuperaciones", 5,
                "golesRecibidos", 0
        );
    }

    // CASO 1: Calcular rendimiento exitoso (Llama a los 2 WebClients y guarda)
    @Test
    void calcular_cuandoTodoEsExitoso_calculaYGuardaCorrectamente() {
        when(jugadorClient.obtenerPosicion(1L)).thenReturn("Delantero");
        when(estadisticaClient.obtenerStats(1L)).thenReturn(statsMock);
        when(repository.findByIdJugador(1L)).thenReturn(Optional.empty());
        when(repository.save(any(Rendimiento.class))).thenReturn(rendimiento);

        RendimientoResponseDTO response = service.calcular(request);

        assertThat(response).isNotNull();
        assertThat(response.getNotaFinal()).isEqualTo(5.5);
        verify(jugadorClient, times(1)).obtenerPosicion(1L);
        verify(estadisticaClient, times(1)).obtenerStats(1L);
        verify(repository, times(1)).save(any(Rendimiento.class));
    }

    // CASO 2: Error en WebClient (Ej. Jugador no existe)
    @Test
    void calcular_cuandoWebClientJugadorFalla_lanzaExcepcion() {
        when(jugadorClient.obtenerPosicion(1L)).thenThrow(new RuntimeException("Jugador no encontrado"));

        assertThatThrownBy(() -> service.calcular(request))
                .isInstanceOf(RuntimeException.class);

        verify(estadisticaClient, never()).obtenerStats(anyLong());
        verify(repository, never()).save(any(Rendimiento.class));
    }

    // CASO 3: Listar rendimientos exitoso
    @Test
    void listarTodos_retornaListaDeRendimientos() {
        when(repository.findAll()).thenReturn(List.of(rendimiento));

        List<RendimientoResponseDTO> response = service.listarTodos();

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getPosicion()).isEqualTo("Delantero");
        verify(repository, times(1)).findAll();
    }
}