package com.example.eureka_client.controllers;

import java.util.HashMap;
import java.util.Map;

import com.example.eureka_common.providers.ApplicationPropertiesProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigsController {
    private final String FIRST_PROPERTY_NAME = "custom.test.clientProperty1";
    private final String SECOND_PROPERTY_NAME = "custom.test.clientProperty2";
    private final String THIRD_PROPERTY_NAME = "custom.test.clientProperty3";
    
    private ApplicationPropertiesProvider applicationPropertiesProvider;
    
    @Autowired
    public ConfigsController(ApplicationPropertiesProvider applicationPropertiesProvider) {
        this.applicationPropertiesProvider = applicationPropertiesProvider;
    }
 
    @RequestMapping(value = "/configs", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> configs() {
        Map<String, String> response = new HashMap<String, String>();
        response.put(FIRST_PROPERTY_NAME, applicationPropertiesProvider.get(FIRST_PROPERTY_NAME));
        response.put(SECOND_PROPERTY_NAME, applicationPropertiesProvider.get(SECOND_PROPERTY_NAME));
        response.put(THIRD_PROPERTY_NAME, applicationPropertiesProvider.get(THIRD_PROPERTY_NAME));

        return ResponseEntity.ok(response);
    }
}