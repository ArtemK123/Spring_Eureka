package com.example.eureka_api_gateway.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.eureka_api_gateway.feign_clients.ProductsFiegnClientDecorator;
import com.example.eureka_common.exceptions.NotFoundException;
import com.example.eureka_common.exceptions.ValidationException;
import com.example.eureka_common.models.Product;
import com.netflix.client.ClientException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class ProductsGatewayController {

    private static final String PRODUCTS_API_IS_NOT_AVAILABLE_MESSAGE = "Products API is not available";
    private static final String PRODUCTS_GATEWAY_CONTROLLER_NAME = "ProductsGatewayController";

    private ProductsFiegnClientDecorator productsFiegnClientDecorator;

    @Autowired
    public ProductsGatewayController(ProductsFiegnClientDecorator productsFiegnClientDecorator) {
        this.productsFiegnClientDecorator = productsFiegnClientDecorator;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("<span>Hello from eureka_api_gateway</span>");
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @CircuitBreaker(name = PRODUCTS_GATEWAY_CONTROLLER_NAME, fallbackMethod = "getAllCircuitBreakerFallback")
    public ResponseEntity<List<Product>> getAll() throws ClientException {
        return this.productsFiegnClientDecorator.getAll();
    }
 
    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @CircuitBreaker(name = PRODUCTS_GATEWAY_CONTROLLER_NAME, fallbackMethod = "getCircuitBreakerFallback")
    public ResponseEntity<Product> get(@PathVariable Integer id) throws NotFoundException, ClientException {
        return this.productsFiegnClientDecorator.get(id);
    }

    @ResponseBody
    @Retry(name = PRODUCTS_GATEWAY_CONTROLLER_NAME, fallbackMethod = "addRetryFallback")
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody Product product) throws ValidationException {
        System.out.println("add is called");
        return this.productsFiegnClientDecorator.add(product);
    }

    @ResponseBody
    @Retry(name = PRODUCTS_GATEWAY_CONTROLLER_NAME, fallbackMethod = "updateRetryFallback")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product product) throws ValidationException, NotFoundException {
        return this.productsFiegnClientDecorator.update(id, product);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Integer id) throws NotFoundException {
        return this.productsFiegnClientDecorator.delete(id);
    }

    public ResponseEntity<List<Product>> getAllCircuitBreakerFallback(ClientException exception) { 
        System.out.println("getAllCircuitBreakerFallback");

        return ResponseEntity.status(500).body(new ArrayList<Product>());
    }

    public ResponseEntity<Product> getCircuitBreakerFallback(Integer id, ClientException exception) { 
        System.out.println("getCircuitBreakerFallback");

        return ResponseEntity.status(500).body(null);
    }

    public ResponseEntity<String> addRetryFallback(Product product, ValidationException exception) {
        System.out.println("addFallback validation exception");

        return ResponseEntity.status(400).body(exception.getMessage());
    }

    public ResponseEntity<String> addRetryFallback(Product product, RuntimeException exception) {
        System.out.println("addFallback");

        return ResponseEntity.status(500).body(PRODUCTS_API_IS_NOT_AVAILABLE_MESSAGE);
    }
    
    public ResponseEntity<String> updateRetryFallback(Integer id, Product product, ValidationException exception) {
        System.out.println("updateRetryFallback validation exception");

        return ResponseEntity.status(400).body(exception.getMessage());
    }

    public ResponseEntity<String> updateRetryFallback(Integer id, Product product, NotFoundException exception) {
        System.out.println("updateRetryFallback not found exception");

        return ResponseEntity.status(404).body(exception.getMessage());
    }

    public ResponseEntity<String> updateRetryFallback(Integer id, Product product, RuntimeException exception) {
        System.out.println("updateRetryFallback");

        return ResponseEntity.status(500).body(PRODUCTS_API_IS_NOT_AVAILABLE_MESSAGE);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }
}