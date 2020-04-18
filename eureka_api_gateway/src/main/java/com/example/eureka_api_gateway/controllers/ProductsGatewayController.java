package com.example.eureka_api_gateway.controllers;

import java.sql.SQLException;
import java.util.List;

import com.example.eureka_api_gateway.feign_clients.ProductsFiegnClient;
import com.example.eureka_common.models.Product;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsGatewayController {
    private ProductsFiegnClient productsFiegnClient;

    @Autowired
    public ProductsGatewayController(ProductsFiegnClient productsFiegnClient) {
        this.productsFiegnClient = productsFiegnClient;
    }
 
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "<span>Hello from eureka_api_gateway</span>";
    }
 
    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getAll() throws SQLException {
        return this.productsFiegnClient.getAll();
    }
 
    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable Integer id) throws SQLException {
        return this.productsFiegnClient.get(id);
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String add(@RequestBody Product product) throws SQLException {
        return this.productsFiegnClient.add(product);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable Integer id, @RequestBody Product product) throws SQLException {
        return this.productsFiegnClient.update(id, product);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id) throws SQLException {
        return this.productsFiegnClient.delete(id);
    }
}