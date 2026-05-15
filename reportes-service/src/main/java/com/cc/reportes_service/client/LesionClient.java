package com.cc.reportes_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Component
public class LesionClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Integer obtenerJugadoresLesionados() {
        List<Map> lesiones = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/lesiones")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();

        if (lesiones == null) return 0;
        return (int) lesiones.stream()
                .filter(l -> "En Tratamiento".equals(l.get("estadoMedico")))
                .count();
    }
}