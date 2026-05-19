package com.cc.recomendaciones_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Component
public class EstadisticaClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Map<String, Object> obtenerEstadisticas(Long idJugador) {
        List<Map<String, Object>> lista = webClientBuilder.build().get()
                .uri("http://localhost:8083/api/estadisticas")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .block();

        return lista.stream()
                .filter(e -> e.get("idJugador").toString().equals(idJugador.toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El jugador no tiene estadisticas registradas"));
    }
}