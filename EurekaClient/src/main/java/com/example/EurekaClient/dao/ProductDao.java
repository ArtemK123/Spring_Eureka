package com.example.EurekaClient.dao;

import java.sql.SQLException;
import com.example.EurekaClient.entities.Product;

public interface ProductDao extends CrudDao<Product> {
    Product getByName(String name) throws SQLException;
}