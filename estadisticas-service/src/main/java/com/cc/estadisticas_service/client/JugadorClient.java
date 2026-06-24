package com.cc.estadisticas_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class JugadorClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Map obtenerJugador(Long idJugador) {
        return webClientBuilder.build().get()
                .uri("http://localhost:8093/api/jugadores/" + idJugador)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}