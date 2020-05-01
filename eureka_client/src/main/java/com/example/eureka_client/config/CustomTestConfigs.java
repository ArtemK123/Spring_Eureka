package com.example.eureka_client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom.test")
public class CustomTestConfigs {
    private String clientProperty1;
    private String clientProperty2;
    private String clientProperty3;

    public String getClientProperty1() {
        return clientProperty1;
    }

    public String getClientProperty2() {
        return clientProperty2;
    }

    public String getClientProperty3() {
        return clientProperty3;
    }

    public void setClientProperty1(String clientProperty1) {
        this.clientProperty1 = clientProperty1;
    }

    public void setClientProperty2(String clientProperty2) {
        this.clientProperty2 = clientProperty2;
    }

    public void setClientProperty3(String clientProperty3) {
        this.clientProperty3 = clientProperty3;
    }
}