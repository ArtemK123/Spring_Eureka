package com.example.eureka_client.domain.dao;

import java.sql.SQLException;

import com.example.eureka_client.domain.entities.Product;

public interface ProductDao extends CrudDao<Product> {
    Product getByName(String name) throws SQLException;
}