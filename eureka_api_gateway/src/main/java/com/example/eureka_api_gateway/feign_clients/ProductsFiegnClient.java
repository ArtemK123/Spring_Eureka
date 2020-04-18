package com.example.eureka_api_gateway.feign_clients;

import java.sql.SQLException;
import java.util.List;

import com.example.eureka_common.models.Product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "eureka-client")
public interface ProductsFiegnClient {

    @GetMapping("/")
    public String home();

    @GetMapping("/products")
    public List<Product> getAll() throws SQLException;

    @GetMapping("/products/{id}")
    public Product get(@PathVariable Integer id) throws SQLException;

    @PostMapping("/products")
    public String add(@RequestBody Product product) throws SQLException;

    @PutMapping("/products/{id}")
    public String update(@PathVariable Integer id, @RequestBody Product product) throws SQLException;

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable Integer id) throws SQLException;
}