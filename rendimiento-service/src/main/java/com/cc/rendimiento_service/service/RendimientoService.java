package com.cc.rendimiento_service.service;

import com.cc.rendimiento_service.client.*;
import com.cc.rendimiento_service.dto.*;
import com.cc.rendimiento_service.entity.Rendimiento;
import com.cc.rendimiento_service.repository.RendimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RendimientoService {

    @Autowired
    private RendimientoRepository repository;

    @Autowired
    private JugadorClient jugadorClient;

    @Autowired
    private EstadisticaClient estadisticaClient;

    public RendimientoResponseDTO calcular(RendimientoRequestDTO dto) {
        log.info("Calculando rendimiento para jugador ID: {}", dto.getIdJugador());

        // eecopilamos la informacion
        String pos = jugadorClient.obtenerPosicion(dto.getIdJugador());
        Map stats = estadisticaClient.obtenerStats(dto.getIdJugador());

        // extraemos los valores clave
        int mins       = (int) stats.get("minutosJugados");
        int goles      = (int) stats.get("golesTotales");
        int recup      = (int) stats.get("recuperaciones");
        int recibidos  = (int) stats.get("golesRecibidos");

        // logica de calificacion por posicion
        double nota = 4.0;
        int impacto = pos.equalsIgnoreCase("Arquero") ? recibidos : goles;

        if (pos.equalsIgnoreCase("Arquero")) {
            // A los arqueros los evaluamos por cuanto salvan y cuanto les anotan
            nota -= (recibidos * 0.5);
            nota += (recup * 0.2);
        } else {
            // A los jugadores de campo los premiamos por goles
            nota += (goles * 0.5);
            nota += (recup * 0.1);
        }

        nota = Math.max(1.0, Math.min(7.0, nota));
        nota = Math.round(nota * 10.0) / 10.0;

        // guardamos en la base de datos
        Rendimiento r = repository.findByIdJugador(dto.getIdJugador()).orElse(new Rendimiento());
        r.setIdJugador(dto.getIdJugador());
        r.setPosicion(pos);
        r.setMinutosJugados(mins);
        r.setGolesImpacto(impacto);
        r.setRecuperaciones(recup);
        r.setNotaFinal(nota);

        Rendimiento guardado = repository.save(r);
        log.info("Rendimiento guardado para jugador ID {} -> nota: {}", dto.getIdJugador(), nota);

        return mapear(guardado);
    }

    // retorna todos los rendimientos calculados y guardados en la BD
    public List<RendimientoResponseDTO> listarTodos() {
        log.info("Listando todos los rendimientos guardados");
        return repository.findAll().stream()
                .map(this::mapear)
                .toList();
    }

    private RendimientoResponseDTO mapear(Rendimiento r) {
        RendimientoResponseDTO res = new RendimientoResponseDTO();
        res.setId(r.getId());
        res.setIdJugador(r.getIdJugador());
        res.setPosicion(r.getPosicion());
        res.setMinutosJugados(r.getMinutosJugados());
        res.setGolesImpacto(r.getGolesImpacto());
        res.setRecuperaciones(r.getRecuperaciones());
        res.setNotaFinal(r.getNotaFinal());
        return res;
    }

}
