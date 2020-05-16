package com.example.eureka_client.service.validators;

import com.example.eureka_common.exceptions.ValidationException;
import com.example.eureka_common.models.Product;

import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    
    private static final String NULLABLE_FIELD_EXCEPTION_MESSAGE = "You must specify a value for field: %s";
    private static final String NEGATIVE_NUMERIC_VALUE_EXCEPTION_MESSAGE = "The negative value is not allowed for field: %s";
    
    public void validate(Product product) throws ValidationException {
        checkNullableFields(product);
        checkNegativeNumbericValues(product);
    }

    private void checkNullableFields(Product product) throws ValidationException {
        if (isNullOrEmpty(product.getName())) {
            throw new ValidationException(String.format(NULLABLE_FIELD_EXCEPTION_MESSAGE, "name"));
        }

        if (product.getCount() == null) {
            throw new ValidationException(String.format(NULLABLE_FIELD_EXCEPTION_MESSAGE, "count"));
        }

        if (product.getPrice() == null) {
            throw new ValidationException(String.format(NULLABLE_FIELD_EXCEPTION_MESSAGE, "price"));
        }

        if (isNullOrEmpty(product.getLink())) {
            throw new ValidationException(String.format(NULLABLE_FIELD_EXCEPTION_MESSAGE, "link"));
        }

        if (isNullOrEmpty(product.getType())) {
            throw new ValidationException(String.format(NULLABLE_FIELD_EXCEPTION_MESSAGE, "type"));
        }

        if (isNullOrEmpty(product.getManufacturer())) {
            throw new ValidationException(String.format(NULLABLE_FIELD_EXCEPTION_MESSAGE, "manufacturer"));
        }
    }

    private void checkNegativeNumbericValues(Product product) throws ValidationException {
        if (product.getCount() < 0) {
            throw new ValidationException(String.format(NEGATIVE_NUMERIC_VALUE_EXCEPTION_MESSAGE, "count"));
        }

        if (product.getPrice() < 0.0) {
            throw new ValidationException(String.format(NEGATIVE_NUMERIC_VALUE_EXCEPTION_MESSAGE, "price"));
        }
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}