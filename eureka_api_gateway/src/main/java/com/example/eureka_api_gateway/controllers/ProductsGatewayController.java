package com.example.eureka_api_gateway.controllers;

import java.sql.SQLException;
import java.util.List;

import com.example.eureka_api_gateway.feign_clients.ProductsFiegnClient;
import com.example.eureka_common.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.retry.annotation.Retry;

import feign.FeignException;

@RestController
public class ProductsGatewayController {
    private static final String PRODUCTS_GATEWAY_CONTROLLER_NAME = "ProductsGatewayController";

    private ProductsFiegnClient productsFiegnClient;

    @Autowired
    public ProductsGatewayController(ProductsFiegnClient productsFiegnClient) {
        this.productsFiegnClient = productsFiegnClient;
    }
 
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("<span>Hello from eureka_api_gateway</span>");
    }
 
    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAll() throws SQLException {
        return this.productsFiegnClient.getAll();
    }
 
    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable Integer id) throws SQLException {
        return this.productsFiegnClient.get(id);
    }

    @ResponseBody
    @Retry(name = PRODUCTS_GATEWAY_CONTROLLER_NAME, fallbackMethod = "addFallback")
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody Product product) throws SQLException {
        return this.productsFiegnClient.add(product);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product product) throws SQLException {
        return this.productsFiegnClient.update(id, product);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Integer id) throws SQLException {
        return this.productsFiegnClient.delete(id);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignStatusException(FeignException feignException) {
        return ResponseEntity
            .status(feignException.status())
            .body(feignException.contentUTF8());
    }

    public ResponseEntity<String> addFallback(Product product, FeignException feignException) {
        System.out.println("Feign exception");
        return ResponseEntity.status(feignException.status()).build();
    }
}