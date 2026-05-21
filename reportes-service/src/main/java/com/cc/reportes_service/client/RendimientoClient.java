package com.cc.reportes_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RendimientoClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private JugadorClient jugadorClient;

    /*
     * Para calcular el promedio del equipo:
     * 1. Trae la lista de jugadores desde jugadores-service (8093)
     * 2. Por cada jugador llama a rendimiento-service (8087) con su ID
     * 3. Rendimiento-service internamente consulta estadisticas-service (8083)
     * 4. Promedia todas las notas validas que llegaron
     *
     * REQUISITO: estadisticas-service (8083) debe tener registros para los jugadores.
     * Si un jugador no tiene estadisticas, se omite del promedio sin romper el calculo.
     */
    public Double obtenerPromedioEquipo() {
        log.info("Calculando promedio del equipo via rendimiento-service (puerto 8087)...");

        List<Map> jugadores = jugadorClient.obtenerListaJugadores();

        if (jugadores.isEmpty()) {
            log.warn("No se encontraron jugadores. Promedio sera 0.0");
            return 0.0;
        }

        double sumaNotas = 0.0;
        int notasValidas = 0;

        for (Map jugador : jugadores) {
            Number idObj = (Number) jugador.get("id");
            if (idObj == null) continue;

            Long idJugador = idObj.longValue();

            try {
                // Construimos el body que espera RendimientoRequestDTO
                Map<String, Long> body = new HashMap<>();
                body.put("idJugador", idJugador);

                Map rendimiento = webClientBuilder.build()
                        .post()
                        .uri("http://localhost:8087/api/rendimientos/calcular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

                if (rendimiento != null && rendimiento.get("notaFinal") != null) {
                    double nota = ((Number) rendimiento.get("notaFinal")).doubleValue();
                    sumaNotas += nota;
                    notasValidas++;
                    log.info("Jugador ID {} -> nota: {}", idJugador, nota);
                }

            } catch (Exception e) {
                // Si el jugador no tiene estadisticas registradas, se omite
                log.warn("Jugador ID {} omitido del promedio. Causa: {}", idJugador, e.getMessage());
            }
        }

        if (notasValidas == 0) {
            log.warn("Ningun jugador tenia estadisticas. Verifica que estadisticas-service (8083) tenga datos. Promedio sera 0.0");
            return 0.0;
        }

        double promedio = Math.round((sumaNotas / notasValidas) * 10.0) / 10.0;
        log.info("Promedio final del equipo: {} (calculado sobre {} jugadores)", promedio, notasValidas);
        return promedio;
    }

}
