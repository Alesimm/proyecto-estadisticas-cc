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
import java.util.List;

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

    // Genera el reporte consultando los 3 microservicios y lo guarda en la BD
    public ReporteResponseDTO generarReporte(ReporteRequestDTO dto) {
        log.info("Iniciando recoleccion de datos para reporte '{}' solicitado por: {}",
                dto.getTipoReporte(), dto.getAutorReporte());

        Integer totalPlantel        = jugadorClient.obtenerTotalJugadores();
        Integer jugadoresLesionados = lesionClient.obtenerJugadoresLesionados();
        Double  promedioEquipo      = rendimientoClient.obtenerPromedioEquipo();

        // La fecha siempre se asigna automaticamente con la fecha de hoy
        String fechaHoy = LocalDate.now().toString();

        Reporte reporte = Reporte.builder()
                .autorReporte(dto.getAutorReporte())
                .tipoReporte(dto.getTipoReporte())
                .fechaGeneracion(fechaHoy)
                .totalPlantel(totalPlantel)
                .jugadoresLesionados(jugadoresLesionados)
                .promedioEquipo(promedioEquipo)
                .build();

        Reporte guardado = repository.save(reporte);
        log.info("Reporte ID {} guardado exitosamente en db_reportes", guardado.getId());

        return convertir(guardado);
    }

    // Retorna todos los reportes guardados en la BD
    public List<ReporteResponseDTO> listarTodos() {
        log.info("Listando todos los reportes guardados");
        return repository.findAll().stream()
                .map(this::convertir)
                .toList();
    }

    // Busca un reporte por su ID
    public ReporteResponseDTO buscarPorId(Long id) {
        log.info("Buscando reporte con ID: {}", id);
        Reporte reporte = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un reporte con ID: " + id));
        return convertir(reporte);
    }

    // Elimina un reporte por su ID
    public void eliminar(Long id) {
        log.info("Eliminando reporte con ID: {}", id);
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No existe un reporte con ID: " + id);
        }
        repository.deleteById(id);
        log.info("Reporte ID {} eliminado", id);
    }

    // Convierte la entidad al DTO de respuesta
    private ReporteResponseDTO convertir(Reporte r) {
        return ReporteResponseDTO.builder()
                .id(r.getId())
                .autorReporte(r.getAutorReporte())
                .tipoReporte(r.getTipoReporte())
                .fechaGeneracion(r.getFechaGeneracion())
                .totalPlantel(r.getTotalPlantel())
                .jugadoresLesionados(r.getJugadoresLesionados())
                .promedioEquipo(r.getPromedioEquipo())
                .build();
    }

}
