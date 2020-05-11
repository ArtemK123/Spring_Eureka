package com.example.eureka_client.api.controllers.exception_handlers;

import org.springframework.http.ResponseEntity;

import com.example.eureka_client.service.exceptions.NotFoundException;
import com.example.eureka_client.service.exceptions.ValidationException;
import com.example.eureka_common.providers.ApplicationPropertiesProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private HttpHeaders httpHeadersWithInstanceId;
    private Logger logger;

    @Autowired
    public RestExceptionHandler(ApplicationPropertiesProvider applicationPropertiesProvider) {
        logger = LoggerFactory.getLogger(RestExceptionHandler.class);

        httpHeadersWithInstanceId = new HttpHeaders();
        httpHeadersWithInstanceId.add("eureka.instance.instance-id", applicationPropertiesProvider.get("eureka.instance.instance-id"));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<String> handleValidationException(ValidationException exception) {
        logger.error(exception.getMessage());

        return ResponseEntity.badRequest().headers(httpHeadersWithInstanceId).body(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        logger.error(exception.getMessage());

        return ResponseEntity.notFound().headers(httpHeadersWithInstanceId).build();
    }
}