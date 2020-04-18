package com.example.eureka_client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationPropertiesProvider {

    @Autowired
    private Environment env;

    public String get(String configKey){
        return env.getProperty(configKey);
    }
}