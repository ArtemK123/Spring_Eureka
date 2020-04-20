package com.example.eureka_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.example.eureka_api_gateway", "com.example.eureka_common"})
@EnableFeignClients
@EnableEurekaClient
public class EurekaApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaApiGatewayApplication.class, args);
	}
}
