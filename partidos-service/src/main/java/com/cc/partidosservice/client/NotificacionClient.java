package com.cc.partidosservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente Feign para comunicarse de forma REST con el microservicio de notificaciones.
 * Cumple con el criterio de interoperabilidad (IE 2.4.1).
 */
@FeignClient(name = "notificaciones-service") // Nombre con el que el MS destino se registra en Eureka
public interface NotificacionClient {

    @PostMapping("/api/notificaciones/enviar")
    void enviarNotificacion(@RequestParam("mensaje") String mensaje);
}