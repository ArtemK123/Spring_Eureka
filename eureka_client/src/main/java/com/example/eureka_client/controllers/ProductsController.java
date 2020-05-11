package com.example.eureka_client.controllers;

import java.util.List;

import com.example.eureka_client.domain.dao.ProductDao;
import com.example.eureka_client.exceptions.ValidationException;
import com.example.eureka_client.validators.ProductValidator;
import com.example.eureka_common.models.Product;
import com.example.eureka_common.providers.ApplicationPropertiesProvider;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {
    private ProductDao dao;
    private ProductValidator productValidator;

    private HttpHeaders httpHeadersWithInstanceId;

    @Autowired
    public ProductsController(
            ProductDao dao,
            ProductValidator productValidator,
            ApplicationPropertiesProvider applicationPropertiesProvider) {
        this.dao = dao;
        this.productValidator = productValidator;
        httpHeadersWithInstanceId = new HttpHeaders();
        httpHeadersWithInstanceId.add("eureka.instance.instance-id", applicationPropertiesProvider.get("eureka.instance.instance-id"));
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().headers(httpHeadersWithInstanceId).body("<span>Hello from eureka_client</span>");
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = this.dao.getAll();
            return ResponseEntity.ok().headers(httpHeadersWithInstanceId).body(products);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable Integer id) {
        Product product = this.dao.get(id);
        if (product != null) {
            return ResponseEntity.ok()
                .headers(httpHeadersWithInstanceId)
                .body(product);
        }
        return ResponseEntity.notFound()
            .headers(httpHeadersWithInstanceId)
            .build();
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody Product product) throws ValidationException {
        productValidator.validate(product);

        Integer createdProductId = this.dao.add(product);
        return ResponseEntity.ok()
            .headers(httpHeadersWithInstanceId)
            .body(String.format("Added successfully. Id - %d", createdProductId));
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product product) throws ValidationException {
        productValidator.validate(product);
        
        Product stored = this.dao.get(id);
        if (stored == null) {
            return ResponseEntity.notFound()
                .headers(httpHeadersWithInstanceId)
                .build();
        }

        this.dao.update(id, product);
        return ResponseEntity.ok()
            .headers(httpHeadersWithInstanceId)
            .body("Updated successfully");
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Product stored = this.dao.get(id);
        if (stored == null) {
            return ResponseEntity.notFound()
                .headers(httpHeadersWithInstanceId)
                .build();
        }

        this.dao.delete(id);
        return ResponseEntity.ok()
            .headers(httpHeadersWithInstanceId)
            .body("Deleted sucessfully");
    }
}