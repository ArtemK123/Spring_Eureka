package com.example.eureka_api_gateway.feign_clients;

import java.sql.SQLException;
import java.util.List;

import com.example.eureka_common.models.Product;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RefreshScope
@FeignClient(name = "${products.service.name}")
public interface ProductsFiegnClient {

    @GetMapping("/")
    public ResponseEntity<String> home();

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAll() throws SQLException;

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> get(@PathVariable Integer id) throws SQLException;

    @PostMapping("/products")
    public ResponseEntity<String> add(@RequestBody Product product) throws SQLException;

    @PutMapping("/products/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product product) throws SQLException;

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) throws SQLException;
}