package com.cc.recomendacionesservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class JugadorClient {
    @Autowired
    private WebClient webClient;

    @Value("${api.jugadores.url}")
    private String url;

    public Map<String, Object> obtenerJugador(Long idJugador) {
        try {
            List<Map> response = webClient.get().uri(url).retrieve().bodyToMono(List.class).block();
            if (response == null) return null;
            return response.stream()
                    .filter(m -> m.get("id") != null && m.get("id").toString().equals(idJugador.toString()))
                    .findFirst().orElse(null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error de negocio: El microservicio de Jugadores (8081) esta apagado o fallo: " + e.getMessage());
        }
    }
}