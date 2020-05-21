package com.example.eureka_kafka_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaKafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaKafkaConsumerApplication.class, args);
	}

}
