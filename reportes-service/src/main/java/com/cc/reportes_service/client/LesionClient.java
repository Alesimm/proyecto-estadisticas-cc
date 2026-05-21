package com.cc.reportes_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class LesionClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Cuenta solo los jugadores que estan actualmente en tratamiento medico
    public Integer obtenerJugadoresLesionados() {
        log.info("Consultando lesiones activas en puerto 8084...");

        List<Map> lesiones = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/lesiones")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();

        if (lesiones == null) return 0;

        int enTratamiento = (int) lesiones.stream()
                .filter(l -> "En Tratamiento".equals(l.get("estadoMedico")))
                .count();

        log.info("Jugadores en tratamiento: {}", enTratamiento);
        return enTratamiento;
    }

}
