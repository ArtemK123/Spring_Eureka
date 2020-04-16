package com.example.EurekaClient.controllers;

import java.sql.SQLException;
import java.util.List;

import com.example.EurekaClient.domain.dao.ProductDao;
import com.example.EurekaClient.domain.entities.Product;

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

    private final String INVALID_PRODUCT_MESSAGE = "Product is not valid! " + 
        "Please, specify all fields. Numeric field should be equal or bigger than 0";

    private final String PRODUCT_NOT_FOUND_MESSAGE = "Item is not found";

    @Autowired
    public ProductsController(ProductDao dao) {
        this.dao = dao;
    }
 
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "<span>Hello from EurekaClient</span>";
    }
 
    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getAll() throws SQLException {
        return this.dao.getAll();
    }
 
    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getById(@PathVariable Integer id) throws SQLException {
        return this.dao.get(id);
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String add(@RequestBody Product product) throws SQLException {
        if (!IsProductValid(product)) {
            return INVALID_PRODUCT_MESSAGE;
        }
        
        Integer createdProductId = this.dao.add(product);
        return String.format("Added successfully. Id - %d", createdProductId);
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable Integer id, @RequestBody Product product) throws SQLException {
        if (!IsProductValid(product)) {
            return INVALID_PRODUCT_MESSAGE;
        }
        
        Product stored = this.dao.get(id);
        if (stored == null) {
            return PRODUCT_NOT_FOUND_MESSAGE;
        }

        this.dao.update(id, product);
        return "Updated successfully";
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id) throws SQLException {
        Product stored = this.dao.get(id);
        if (stored == null) {
            return PRODUCT_NOT_FOUND_MESSAGE;
        }

        this.dao.delete(id);
        return "Deleted sucessfully";
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