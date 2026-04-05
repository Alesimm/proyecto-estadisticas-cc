package com.cc.formaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FormacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormacionesApplication.class, args);
	}

}
