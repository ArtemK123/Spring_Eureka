package com.example.eureka_api_gateway.feign_clients;

import java.util.List;

import com.example.eureka_common.exceptions.NotFoundException;
import com.example.eureka_common.exceptions.ValidationException;
import com.example.eureka_common.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.netflix.client.ClientException;

import feign.FeignException;

@Component
public class ProductsFiegnClientDecorator {
    private ProductsFiegnClient productsFiegnClient;

    @Autowired
    public ProductsFiegnClientDecorator(ProductsFiegnClient productsFiegnClient) {
        this.productsFiegnClient = productsFiegnClient;
    }

    public ResponseEntity<String> home() {
        return productsFiegnClient.home();
    }

    public ResponseEntity<List<Product>> getAll() throws ClientException {
        try {
            return productsFiegnClient.getAll();
        }
        catch (RuntimeException runtimeException) {
            Throwable nestedException = runtimeException.getCause();
            if (nestedException instanceof ClientException) {
                throw (ClientException)nestedException;
            }
            throw runtimeException;
        }
    }

    public ResponseEntity<Product> get(Integer id) throws ClientException, NotFoundException {
        try {
            return productsFiegnClient.get(id);
        }
        catch (FeignException feignException) {
            if (feignException.status() == 404) {
                throw new NotFoundException(feignException.contentUTF8());
            }
            throw feignException;
        }
        catch (RuntimeException runtimeException) {
            Throwable nestedException = runtimeException.getCause();
            if (nestedException instanceof ClientException) {
                throw (ClientException)nestedException;
            }
            throw runtimeException;
        }
    }

    public ResponseEntity<String> add(Product product) throws ValidationException {
        try {
            return productsFiegnClient.add(product);
        }
        catch (FeignException feignException) {
            if (feignException.status() == 400) {
                throw new ValidationException(feignException.contentUTF8());
            }
            throw feignException;
        }
    }

    public ResponseEntity<String> update(Integer id, Product product) throws ValidationException, NotFoundException, FeignException  {
        try {
            return productsFiegnClient.update(id, product);
        }
        catch (FeignException feignException) {
            int statusCode = feignException.status();
            if (statusCode == 400) {
                throw new ValidationException(feignException.contentUTF8());
            }
            else if (statusCode == 404) {
                throw new NotFoundException(feignException.contentUTF8());
            }
            throw feignException;
        }
    }

    public ResponseEntity<String> delete(Integer id) throws NotFoundException, FeignException {
        try {
            return productsFiegnClient.delete(id);
        }
        catch (FeignException feignException) {
            if (feignException.status() == 404) {
                throw new NotFoundException(feignException.contentUTF8());
            }
            throw feignException;
        }
    }
}