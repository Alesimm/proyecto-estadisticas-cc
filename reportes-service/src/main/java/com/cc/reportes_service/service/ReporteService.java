package com.cc.reportes_service.service;

import com.cc.reportes_service.client.JugadorClient;
import com.cc.reportes_service.client.LesionClient;
import com.cc.reportes_service.client.RendimientoClient;
import com.cc.reportes_service.dto.ReporteRequestDTO;
import com.cc.reportes_service.dto.ReporteResponseDTO;
import com.cc.reportes_service.entity.Reporte;
import com.cc.reportes_service.repository.ReporteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class ReporteService {

    @Autowired
    private ReporteRepository repository;
    @Autowired
    private JugadorClient jugadorClient;
    @Autowired
    private LesionClient lesionClient;
    @Autowired
    private RendimientoClient rendimientoClient;

    public ReporteResponseDTO generarReporte(ReporteRequestDTO dto) {
        log.info("Iniciando recoleccion de datos para el reporte de tipo: {}", dto.getTipoReporte());

        Integer total = jugadorClient.obtenerTotalJugadores();
        Integer lesionados = lesionClient.obtenerJugadoresLesionados();
        Double promedio = rendimientoClient.obtenerPromedioEquipo();

        Reporte reporte = Reporte.builder()
                .autorReporte(dto.getAutorReporte())
                .tipoReporte(dto.getTipoReporte())
                .fechaGeneracion(dto.getFechaGeneracion() != null ? dto.getFechaGeneracion() : LocalDate.now().toString())
                .totalPlantel(total)
                .jugadoresLesionados(lesionados)
                .promedioEquipo(promedio)
                .build();

        Reporte guardado = repository.save(reporte);
        log.info("Reporte guardado exitosamente con ID: {}", guardado.getId());

        return ReporteResponseDTO.builder()
                .id(guardado.getId())
                .autorReporte(guardado.getAutorReporte())
                .tipoReporte(guardado.getTipoReporte())
                .fechaGeneracion(guardado.getFechaGeneracion())
                .totalPlantel(guardado.getTotalPlantel())
                .jugadoresLesionados(guardado.getJugadoresLesionados())
                .promedioEquipo(guardado.getPromedioEquipo())
                .build();
    }
}