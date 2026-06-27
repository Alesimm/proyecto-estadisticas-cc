package com.cc.reportes_service.service;

import com.cc.reportes_service.client.*;
import com.cc.reportes_service.dto.*;
import com.cc.reportes_service.entity.Reporte;
import com.cc.reportes_service.repository.ReporteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock private ReporteRepository repository;
    @Mock private JugadorClient jugadorClient;
    @Mock private LesionClient lesionClient;
    @Mock private RendimientoClient rendimientoClient;
    @InjectMocks private ReporteService service;

    @Test
    void test_generar_reporte_exitoso() {
        when(jugadorClient.obtenerTotalJugadores()).thenReturn(20);
        when(lesionClient.obtenerJugadoresLesionados()).thenReturn(2);
        when(rendimientoClient.obtenerPromedioEquipo()).thenReturn(5.5);
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        ReporteRequestDTO req = new ReporteRequestDTO();
        req.setAutorReporte("cris");
        req.setTipoReporte("Mensual");

        ReporteResponseDTO res = service.generarReporte(req);

        assertThat(res).isNotNull();
        verify(repository, times(1)).save(any());
    }

    @Test
    void test_generar_reporte_falla_api() {
        when(jugadorClient.obtenerTotalJugadores()).thenThrow(new RuntimeException("api caida"));

        ReporteRequestDTO req = new ReporteRequestDTO();
        req.setAutorReporte("cris");
        req.setTipoReporte("Mensual");

        assertThatThrownBy(() -> service.generarReporte(req))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void test_listar_reportes() {
        when(repository.findAll()).thenReturn(List.of(new Reporte()));
        assertThat(service.listarTodos()).hasSize(1);
    }
}