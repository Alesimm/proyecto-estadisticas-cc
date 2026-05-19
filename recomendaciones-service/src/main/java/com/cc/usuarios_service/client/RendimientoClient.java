package com.cc.usuarios_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class RendimientoClient {
    @Autowired
    private WebClient webClient;

    @Value("${api.rendimiento.url}")
    private String url;

    public Map<String, Object> obtenerRendimiento(Long idJugador) {
        try {
            List<Map> response = webClient.get().uri(url).retrieve().bodyToMono(List.class).block();
            if (response == null) return null;
            return response.stream()
                    .filter(m -> {
                        Object idVal = m.get("id_jugador") != null ? m.get("id_jugador") : (m.get("idJugador") != null ? m.get("idJugador") : m.get("id"));
                        return idVal != null && idVal.toString().equals(idJugador.toString());
                    })
                    .findFirst().orElse(null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error de negocio: El microservicio de Rendimiento (8087) esta apagado o fallo: " + e.getMessage());
        }
    }
}