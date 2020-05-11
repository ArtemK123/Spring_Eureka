package com.example.eureka_client.controllers.exception_handlers;

import com.example.eureka_common.providers.ApplicationPropertiesProvider;
import com.example.eureka_client.exceptions.ValidationException;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private HttpHeaders httpHeadersWithInstanceId;

    @Autowired
    public RestExceptionHandler(ApplicationPropertiesProvider applicationPropertiesProvider) {
        httpHeadersWithInstanceId = new HttpHeaders();
        httpHeadersWithInstanceId.add("eureka.instance.instance-id", applicationPropertiesProvider.get("eureka.instance.instance-id"));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<String> handleValidationException(ValidationException exception) {
        System.err.println(exception.getMessage());

        return ResponseEntity.badRequest().headers(httpHeadersWithInstanceId).body(exception.getMessage());
    }
}