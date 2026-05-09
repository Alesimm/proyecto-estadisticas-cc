package com.cc.partidosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Clase principal que arranca el microservicio de Partidos.
 */
@SpringBootApplication
@EnableDiscoveryClient // Registra el servicio en Eureka para ser encontrado por otros
@EnableFeignClients    // Habilita OpenFeign para la comunicación con otros microservicios (IE 2.4.1)
public class PartidosserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PartidosserviceApplication.class, args);
	}
}