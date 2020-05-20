package com.example.eureka_kafka_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductMessagesConsumer {
    
    private Logger logger;

    public ProductMessagesConsumer() {
        logger = LoggerFactory.getLogger(ProductMessagesConsumer.class);
    }

    // topics use SpEL expression in order to get topic name from application.properties file
    @KafkaListener(id = "EurekaAdd", topics = "#{'${kafka.topic.add}'}", containerFactory = "singleFactory")
    public void consumeAddMessage(String message) {
        logger.info("Add. " + message);
    }

    @KafkaListener(id = "EurekaUpdate", topics = "#{'${kafka.topic.update}'}", containerFactory = "singleFactory")
    public void consumeUpdateMessage(String message) {
        logger.info("Update. " + message);
    }
}