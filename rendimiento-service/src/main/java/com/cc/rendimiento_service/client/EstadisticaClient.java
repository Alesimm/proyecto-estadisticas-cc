package com.cc.rendimiento_service.client;

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

    // traemo todas las estadisticas y filtramos al jugador que nos interesa
    public Map obtenerStats(Long id) {
        List<Map> lista = webClientBuilder.build().get()
                .uri("http://localhost:8083/api/estadisticas")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map>>() {})
                .block();

        return lista.stream()
                .filter(stat -> stat.get("idJugador").toString().equals(id.toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El jugador todavia no tiene minutos jugados"));
    }
}