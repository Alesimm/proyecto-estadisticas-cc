package com.cc.recomendaciones_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Component
public class JugadorClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Map<String, Object> obtenerJugador(Long id) {
        List<Map<String, Object>> lista = webClientBuilder.build().get()
                .uri("http://localhost:8093/api/jugadores")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .block();

        return lista.stream()
                .filter(j -> j.get("id").toString().equals(id.toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado en el sistema"));
    }
}