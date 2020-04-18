package com.example.eureka_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"com.example.eureka_client", "com.example.eureka_common"})
@EnableEurekaClient
public class EurekaClientApplication {
	public static void main(final String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}
}
