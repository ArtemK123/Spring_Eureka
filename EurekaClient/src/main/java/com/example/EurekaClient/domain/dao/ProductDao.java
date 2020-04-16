package com.example.EurekaClient.domain.dao;

import java.sql.SQLException;

import com.example.EurekaClient.domain.entities.Product;

public interface ProductDao extends CrudDao<Product> {
    Product getByName(String name) throws SQLException;
}