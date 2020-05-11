package com.example.eureka_client.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import com.example.eureka_client.domain.config.JdbcTemplateProvider;
import com.example.eureka_client.domain.row_mappers.ProductRowMapper;
import com.example.eureka_client.domain.table_creators.ProductsTableCreator;
import com.example.eureka_common.models.Product;

@Component
public class ProductDao {
    private JdbcTemplate jdbcTemplate;
    private ProductsTableCreator tableCreator;

    private final String TABLE_NAME = "products"; 

    @Autowired
    public ProductDao(JdbcTemplateProvider jdbcTemplateProvider) {
        jdbcTemplate = jdbcTemplateProvider.get();
        tableCreator = new ProductsTableCreator(jdbcTemplate);

        tableCreator.create();
    }

    public Integer add(Product product) {
        product.setId(product.hashCode());
        
        String query = String.format(
            "INSERT INTO %s " +
                "(id, name, count, price, link, type, manufacturer) VALUES " +
                "(%d, '%s', %d, %f, '%s', '%s', '%s')",
                TABLE_NAME,
                product.getId(),
                product.getName(),
                product.getCount(),
                product.getPrice(),
                product.getLink(),
                product.getType(),
                product.getManufacturer());

        jdbcTemplate.update(query);
        return product.getId();
    }

    public List<Product> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(query, new ProductRowMapper());
    }

    public Product get(Integer id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        try {
            return this.jdbcTemplate.queryForObject(query, new Object[]{id}, new ProductRowMapper());
        }
        catch(Exception exception) {
            return null;
        }
    }

    public Product getByName(String name) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE name=?";
    
        try {
            return this.jdbcTemplate.queryForObject(query, new Object[]{name}, new ProductRowMapper());
        }
        catch(Exception exception) {
            return null;
        }
    }

    public void update(Integer id, Product updatedProduct) {
        String query = String.format(
                "UPDATE %s SET name='%s', count=%d, price=%f, link='%s', type='%s', manufacturer='%s' WHERE id=%d",
                TABLE_NAME,
                updatedProduct.getName(),
                updatedProduct.getCount(),
                updatedProduct.getPrice(),
                updatedProduct.getLink(),
                updatedProduct.getType(),
                updatedProduct.getManufacturer(),
                id);

        this.jdbcTemplate.update(query);
    }

    public void delete(Integer id) {
        String query = String.format("DELETE FROM %s WHERE id=%d", TABLE_NAME, id);
        this.jdbcTemplate.update(query);
    }
}