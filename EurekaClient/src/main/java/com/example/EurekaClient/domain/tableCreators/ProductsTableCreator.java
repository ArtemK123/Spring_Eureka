package com.example.EurekaClient.domain.tableCreators;

import org.springframework.jdbc.core.JdbcTemplate;

public class ProductsTableCreator {

    private JdbcTemplate jdbcTemplate;

    public ProductsTableCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String TABLE_NAME_WITH_SCHEME = "public.products"; 
    
    public void create() {
        String query = String.format(
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_WITH_SCHEME + " (" +
                "id integer PRIMARY KEY, " +
                "name character varying(100) NOT NULL, " +
                "price numeric(6,2) NOT NULL DEFAULT 0.00, " +
                "link character varying(100), " +
                "type character varying(100), " +
                "manufacturer character varying(100), " +
                "count integer NOT NULL)");

        jdbcTemplate.update(query);
    }
}