package com.ccanalytics.jugadores_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class 	JugadoresServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JugadoresServiceApplication.class, args);
	}

}
