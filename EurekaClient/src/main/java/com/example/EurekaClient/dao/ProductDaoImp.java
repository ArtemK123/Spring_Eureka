package com.example.EurekaClient.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import com.example.EurekaClient.config.JdbcTemplateProvider;
import com.example.EurekaClient.entities.Product;
import com.example.EurekaClient.rowMappers.ProductRowMapper;
import com.example.EurekaClient.tableCreators.ProductsTableCreator;
import com.example.EurekaClient.tableCreators.TableCreator;

@Component
public class ProductDaoImp implements ProductDao {
    private JdbcTemplate jdbcTemplate;
    private TableCreator tableCreator;

    private final String TABLE_NAME = "products"; 

    @Autowired
    public ProductDaoImp(JdbcTemplateProvider jdbcTemplateProvider) {
        jdbcTemplate = jdbcTemplateProvider.get();
        tableCreator = new ProductsTableCreator(jdbcTemplate);

        tableCreator.create();
    }

    public Product add(Product product) {
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
        return product;
    }

    public List<Product> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(query, new ProductRowMapper());
    }

    public Product getById(Integer id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        return this.jdbcTemplate.queryForObject(query, new Object[]{id}, new ProductRowMapper());
    }

    public Product getByName(String name) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE name=?";
        return this.jdbcTemplate.queryForObject(query, new Object[]{name}, new ProductRowMapper());
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