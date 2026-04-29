package com.cc.partidosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Esto permite que Eureka Server te encuentre
public class PartidosserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PartidosserviceApplication.class, args);
	}
}