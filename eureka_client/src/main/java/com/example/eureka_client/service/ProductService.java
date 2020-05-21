package com.example.eureka_client.service;

import java.util.List;

import com.example.eureka_client.domain.dao.ProductDao;
import com.example.eureka_common.exceptions.NotFoundException;
import com.example.eureka_common.exceptions.ValidationException;
import com.example.eureka_client.service.kafka_producer.ProductMessagesService;
import com.example.eureka_client.service.validators.ProductValidator;
import com.example.eureka_common.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Product with id=%d is not found";

    private ProductDao dao;
    private ProductValidator productValidator;
    private ProductMessagesService productMessagesService;

    @Autowired
    public ProductService(ProductDao dao, ProductValidator productValidator, ProductMessagesService productMessagesService) {
        this.dao = dao;
        this.productValidator = productValidator;
        this.productMessagesService = productMessagesService;
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
        try {
            productValidator.validate(product);
        }
        catch(ValidationException validationException) {
            productMessagesService.sendAddMessage(0, validationException.getMessage());
            throw validationException;
        }

        Integer createdProductId = this.dao.add(product);

        productMessagesService.sendAddMessage(createdProductId, "Added successfully");
        return createdProductId;
    }

    public Integer update(Integer id, Product product) throws ValidationException, NotFoundException {
        try {
            productValidator.validate(product);
        }
        catch(ValidationException validationException) {
            productMessagesService.sendUpdateMessage(id, validationException.getMessage());
            throw validationException;
        }
        
        Product stored = this.dao.get(id);
        if (stored == null) {
            String errorMessage = String.format(NOT_FOUND_EXCEPTION_MESSAGE, id);
            productMessagesService.sendUpdateMessage(id, errorMessage);
            throw new NotFoundException(errorMessage);   
        }

        this.dao.update(id, product);
        productMessagesService.sendUpdateMessage(id, "Updated successfully");
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