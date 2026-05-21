package com.cc.reportes_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JugadorClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Trae todos los jugadores y retorna cuantos hay
    public Integer obtenerTotalJugadores() {
        log.info("Consultando total de jugadores en puerto 8093...");

        List<Map> jugadores = webClientBuilder.build()
                .get()
                .uri("http://localhost:8093/api/jugadores")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();

        int total = (jugadores != null) ? jugadores.size() : 0;
        log.info("Total jugadores: {}", total);
        return total;
    }

    // Trae la lista completa de jugadores con sus IDs para el calculo de rendimiento
    public List<Map> obtenerListaJugadores() {
        List<Map> jugadores = webClientBuilder.build()
                .get()
                .uri("http://localhost:8093/api/jugadores")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();

        return (jugadores != null) ? jugadores : List.of();
    }

}
