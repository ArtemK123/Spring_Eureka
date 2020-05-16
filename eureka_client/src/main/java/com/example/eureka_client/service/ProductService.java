package com.example.eureka_client.service;

import java.util.List;

import com.example.eureka_client.domain.dao.ProductDao;
import com.example.eureka_common.exceptions.NotFoundException;
import com.example.eureka_common.exceptions.ValidationException;
import com.example.eureka_client.service.validators.ProductValidator;
import com.example.eureka_common.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Product with id=%d is not found";

    private ProductDao dao;
    private ProductValidator productValidator;

    @Autowired
    public ProductService(ProductDao dao, ProductValidator productValidator) {
        this.dao = dao;
        this.productValidator = productValidator;
    }

    public List<Product> getAll() {
        return this.dao.getAll();
    }

    public Product get(Integer id) throws NotFoundException {
        Product product = this.dao.get(id);
        if (product == null) {
            throw new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, id));            
        }

        return product;
    }

    public Integer add(Product product) throws ValidationException {
        productValidator.validate(product);

        Integer createdProductId = this.dao.add(product);
        return createdProductId;
    }

    public Integer update(Integer id, Product product) throws ValidationException, NotFoundException {
        productValidator.validate(product);
        
        Product stored = this.dao.get(id);
        if (stored == null) {
            throw new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, id));   
        }

        this.dao.update(id, product);
        return id;
    }

    public Integer delete(Integer id) throws NotFoundException {
        Product stored = this.dao.get(id);
        if (stored == null) {
            throw new NotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, id));
        }

        this.dao.delete(id);
        return id;
    }
}