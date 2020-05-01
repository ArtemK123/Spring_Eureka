package com.example.eureka_api_gateway.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigsController {
    private static final String FIRST_PROPERTY_NAME = "custom.test.gatewayProperty1";
    private static final String SECOND_PROPERTY_NAME = "custom.test.gatewayProperty2";
    private static final String THIRD_PROPERTY_NAME = "custom.test.gatewayProperty3";
    
    @Value("${" + FIRST_PROPERTY_NAME + "}")
    private String firstPropertyValue;
    
    @Value("${" + SECOND_PROPERTY_NAME + "}")
    private String secondPropertyValue;
    
    @Value("${" + THIRD_PROPERTY_NAME + "}")
    private String thirdPropertyValue;

    @RequestMapping(value = "/configs", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> configs() {
        Map<String, String> response = new HashMap<String, String>();
        response.put(FIRST_PROPERTY_NAME, firstPropertyValue);
        response.put(SECOND_PROPERTY_NAME, secondPropertyValue);
        response.put(THIRD_PROPERTY_NAME, thirdPropertyValue);

        return ResponseEntity.ok(response);
    }
}