package com.example.eureka_client.service.kafka_producer;

import java.io.Serializable;

public class EntityEditedMessage implements Serializable {
    private static final long serialVersionUID = 8210468823325555714L;

    private boolean isSuccessful;
    private String resultMessage;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public void setSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }
}