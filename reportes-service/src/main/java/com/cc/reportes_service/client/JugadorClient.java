package com.cc.reportes_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Component
public class JugadorClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Integer obtenerTotalJugadores() {
        List<Map> jugadores = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/jugadores")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
        return (jugadores != null) ? jugadores.size() : 0;
    }
}