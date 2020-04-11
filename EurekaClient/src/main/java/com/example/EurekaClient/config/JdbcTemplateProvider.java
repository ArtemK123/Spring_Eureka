package com.example.EurekaClient.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateProvider {
    private ApplicationPropertiesProvider applicationPropertiesProvider;

    @Autowired
    public JdbcTemplateProvider(ApplicationPropertiesProvider applicationPropertiesProvider) {
        this.applicationPropertiesProvider = applicationPropertiesProvider;
    }

    private final String DRIVER_CLASS_NAME_PROPERTY_NAME = "spring.datasource.driver-class-name";
    private final String URL_PROPERTY_NAME = "spring.datasource.url";
    private final String USERNAME_PROPERTY_NAME = "spring.datasource.username";
    private final String PASSWORD_PROPERTY_NAME = "spring.datasource.password";

    private static DataSource dataSource = null;

    public JdbcTemplate get() {
        if (dataSource == null) {
            dataSource = CreateDataSource();
        }
        
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);

        return template;
    }

    private DataSource CreateDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(applicationPropertiesProvider.get(DRIVER_CLASS_NAME_PROPERTY_NAME));
        ds.setUrl(applicationPropertiesProvider.get(URL_PROPERTY_NAME));
        ds.setUsername(applicationPropertiesProvider.get(USERNAME_PROPERTY_NAME));
        ds.setPassword(applicationPropertiesProvider.get(PASSWORD_PROPERTY_NAME));
        return ds;
    }
}