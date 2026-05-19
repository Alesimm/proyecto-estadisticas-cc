package com.cc.recomendaciones_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.Map;

@Component
public class RendimientoClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Map obtenerNota(Long idJugador) {
        Map<String, Long> body = new HashMap<>();
        body.put("idJugador", idJugador);

        return webClientBuilder.build().post()
                .uri("http://localhost:8087/api/rendimientos/calcular")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}