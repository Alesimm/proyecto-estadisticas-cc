package com.cc.rendimiento_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Preparamos el cliente web para que Spring lo inyecte donde lo necesitemos
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}