package com.example.eureka_client.domain.row_mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.eureka_common.models.Product;

public class ProductRowMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();
        
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setCount(resultSet.getInt("count"));
        product.setPrice(resultSet.getFloat("price"));
        product.setLink(resultSet.getString("link"));
        product.setType(resultSet.getString("type"));
        product.setManufacturer(resultSet.getString("manufacturer"));

        return product;
    }
}