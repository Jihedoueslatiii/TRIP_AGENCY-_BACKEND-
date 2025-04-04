package com.esprit.ms.apigateway4sa11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGateway4Sa11Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiGateway4Sa11Application.class, args);
	}

	@Bean
	public RouteLocator getRouteApiGateway(RouteLocatorBuilder builder)
	{
		return builder.routes()

				.route("vol-route", r -> r
						.path("/api/flights/**")
						.uri("lb://Vol"))  // Match the service name
				.build();



	}
}
