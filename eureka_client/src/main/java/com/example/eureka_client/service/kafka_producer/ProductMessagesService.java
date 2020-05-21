package com.example.eureka_client.service.kafka_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductMessagesService {
    private KafkaTemplate<Long, String> kafkaTemplate;

    @Value("${kafka.topic.add}")
    private String addTopicName;

    @Value("${kafka.topic.update}")
    private String updateTopicName;

    @Autowired
    public ProductMessagesService(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAddMessage(int productId, String resultMessage) {
        String messageToSend = String.format("Add the entity with id=%d. %s", productId, resultMessage);
        kafkaTemplate.send(addTopicName, messageToSend);
    } 
    
    public void sendUpdateMessage(int productId, String resultMessage) {
        String messageToSend = String.format("Update the entity with id=%d. %s", productId, resultMessage);
        kafkaTemplate.send(updateTopicName, messageToSend);
    }
}