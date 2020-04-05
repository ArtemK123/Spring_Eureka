package com.example.EurekaClient.controllers;

import java.sql.SQLException;
import java.util.List;

import com.example.EurekaClient.dao.ProductDao;
import com.example.EurekaClient.dao.ProductDaoImp;
import com.example.EurekaClient.entities.Product;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class ProductController {
 
    private final ProductDao dao;

    public ProductController() {
        dao = new ProductDaoImp();
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
        return this.dao.getById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String add(@RequestBody Product product) throws SQLException {
        Product createdProduct = this.dao.add(product);
        return String.format("Added successfully. Id - %d", createdProduct.getId());
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable Integer id, @RequestBody Product product) throws SQLException {
        this.dao.update(id, product);
        return "Updated successfully";
    }

    @ResponseBody
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id) throws SQLException {
        this.dao.delete(id);
        return "Deleted sucessfully";
    }
}