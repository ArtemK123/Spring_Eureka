package com.example.eureka_client.controllers;

import java.sql.SQLException;
import java.util.List;

import com.example.eureka_client.domain.dao.ProductDao;
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

    private final String INVALID_PRODUCT_MESSAGE = "Product is not valid! "
            + "Please, specify all fields. Numeric field should be equal or bigger than 0";

    private HttpHeaders httpHeadersWithInstanceId;

    @Autowired
    public ProductsController(ProductDao dao, ApplicationPropertiesProvider applicationPropertiesProvider) {
        this.dao = dao;
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
    public ResponseEntity<List<Product>> getAll() throws SQLException {
        List<Product> products = this.dao.getAll();
        return ResponseEntity.ok().headers(httpHeadersWithInstanceId).body(products);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable Integer id) throws SQLException {
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
    public ResponseEntity<String> add(@RequestBody Product product) throws SQLException {
        if (!IsProductValid(product)) {
            return ResponseEntity.badRequest()
                .headers(httpHeadersWithInstanceId)
                .body(INVALID_PRODUCT_MESSAGE);
        }

        Integer createdProductId = this.dao.add(product);
        return ResponseEntity.ok()
            .headers(httpHeadersWithInstanceId)
            .body(String.format("Added successfully. Id - %d", createdProductId));
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product product) throws SQLException {
        if (!IsProductValid(product)) {
            return ResponseEntity.badRequest()
                .headers(httpHeadersWithInstanceId)
                .body(INVALID_PRODUCT_MESSAGE);
        }
        
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
    public ResponseEntity<String> delete(@PathVariable Integer id) throws SQLException {
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

    
    private boolean IsProductValid(Product product) {
        return !isNullOrEmpty(product.getName())
            && product.getCount() != null && product.getCount() >= 0
            && product.getPrice() != null && product.getPrice() >= 0.0
            && !isNullOrEmpty(product.getLink())
            && !isNullOrEmpty(product.getType())
            && !isNullOrEmpty(product.getManufacturer());
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}