package com.example.offresvoyage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OffresVoyageApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffresVoyageApplication.class, args);
	}

}
