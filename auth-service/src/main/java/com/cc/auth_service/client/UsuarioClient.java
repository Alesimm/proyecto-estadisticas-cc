package com.cc.auth_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UsuarioClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Map<String, Object> buscarUsuarioPorCorreo(String correo) {
        try {
            List<Map<String, Object>> usuarios = webClientBuilder.build().get()
                    .uri("http://localhost:8090/api/usuarios")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                    .block();

            if (usuarios == null || usuarios.isEmpty()) {
                throw new IllegalArgumentException("No hay usuarios registrados en el sistema.");
            }

            return usuarios.stream()
                    .filter(u -> u.get("correo") != null && u.get("correo").toString().equalsIgnoreCase(correo))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Credenciales invalidas (Correo no encontrado)."));
        } catch (IllegalArgumentException ie) {
            throw ie;
        } catch (Exception e) {
            log.error("Fallo comunicacion con Usuarios: {}", e.getMessage());
            throw new IllegalArgumentException("El servicio de usuarios no esta disponible.");
        }
    }
}