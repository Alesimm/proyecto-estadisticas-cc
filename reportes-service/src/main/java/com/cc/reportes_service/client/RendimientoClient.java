package com.cc.reportes_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RendimientoClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Double obtenerPromedioEquipo() {
        try {
            // PASO 1: Vamos a nuestro propio servicio de jugadores a buscar los IDs
            List<Map> jugadores = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/api/jugadores")
                    .retrieve()
                    .bodyToFlux(Map.class)
                    .collectList()
                    .block();

            if (jugadores == null || jugadores.isEmpty()) return 0.0;

            double sumaNotas = 0.0;
            int notasValidas = 0;

            // PASO 2: Por cada jugador, le disparamos un POST al microservicio de tu compañero
            for (Map jugador : jugadores) {
                Number idObj = (Number) jugador.get("id");
                if (idObj == null) continue;
                Long idJugador = idObj.longValue();

                try {
                    // Armamos el JSON que espera el RendimientoRequestDTO de tu compañero
                    Map<String, Long> requestBody = Map.of("idJugador", idJugador);

                    Map rendimiento = webClientBuilder.build()
                            .post()
                            .uri("http://localhost:8087/api/rendimientos/calcular")
                            .bodyValue(requestBody)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block();

                    // Extraemos la notaFinal del RendimientoResponseDTO
                    if (rendimiento != null && rendimiento.get("notaFinal") != null) {
                        sumaNotas += ((Number) rendimiento.get("notaFinal")).doubleValue();
                        notasValidas++;
                    }
                } catch (Exception e) {
                    log.warn("El jugador ID " + idJugador + " no tiene rendimiento registrado o hubo un error.");
                }
            }

            // PASO 3: Calculamos el promedio matematico final
            return notasValidas > 0 ? (sumaNotas / notasValidas) : 0.0;

        } catch (Exception e) {
            log.error("Fallo general al intentar promediar el rendimiento: " + e.getMessage());
            return 0.0;
        }
    }
}