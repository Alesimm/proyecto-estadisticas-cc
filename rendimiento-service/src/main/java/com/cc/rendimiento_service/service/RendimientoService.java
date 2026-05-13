package com.cc.rendimiento_service.service;

import com.cc.rendimiento_service.client.*;
import com.cc.rendimiento_service.dto.*;
import com.cc.rendimiento_service.entity.Rendimiento;
import com.cc.rendimiento_service.repository.RendimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class RendimientoService {

    @Autowired
    private RendimientoRepository repository;

    @Autowired
    private JugadorClient jugadorClient;

    @Autowired
    private EstadisticaClient estadisticaClient;

    public RendimientoResponseDTO calcular(RendimientoRequestDTO dto) {

        // 1. Recopilamos la informacion usando nuestros clientes
        String pos = jugadorClient.obtenerPosicion(dto.getIdJugador());
        Map stats = estadisticaClient.obtenerStats(dto.getIdJugador());

        // 2. Extraemos los valores clave de forma limpia
        int mins = (int) stats.get("minutosJugados");
        int goles = (int) stats.get("golesTotales");
        int recup = (int) stats.get("recuperaciones");
        int recibidos = (int) stats.get("golesRecibidos");

        // 3. Logica de calificacion por posicion
        double nota = 4.0; // Todos parten con nota base
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

        // 4. Aseguramos que la nota no rompa la escala del 1 al 7
        nota = Math.max(1.0, Math.min(7.0, nota));
        nota = Math.round(nota * 10.0) / 10.0;

        // 5. Guardamos en la base de datos (si ya existe, lo pisa)
        Rendimiento r = repository.findByIdJugador(dto.getIdJugador()).orElse(new Rendimiento());
        r.setIdJugador(dto.getIdJugador());
        r.setPosicion(pos);
        r.setMinutosJugados(mins);
        r.setGolesImpacto(impacto);
        r.setRecuperaciones(recup);
        r.setNotaFinal(nota);

        Rendimiento guardado = repository.save(r);

        // 6. Devolvemos la respuesta formateada
        return mapear(guardado);
    }

    // Convertidor simple de Entidad a DTO
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