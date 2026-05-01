package com.cc.estadisticas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EstadisticasServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EstadisticasServiceApplication.class, args);
	}
}