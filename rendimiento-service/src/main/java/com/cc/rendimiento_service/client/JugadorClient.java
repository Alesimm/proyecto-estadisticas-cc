package com.cc.rendimiento_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class JugadorClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public String obtenerPosicion(Long id) {
        Map res = webClientBuilder.build().get()
                .uri("http://localhost:8081/api/jugadores/" + id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return res.get("posicion").toString();
    }
}