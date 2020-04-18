package com.example.eureka_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class EurekaApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaApiGatewayApplication.class, args);
	}
}
