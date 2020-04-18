package com.example.eureka_client.domain.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<TEntity> {
    Integer add(TEntity entity) throws SQLException;
    void delete(Integer id) throws SQLException;
    void update(Integer id, TEntity updatedEntity) throws SQLException;

    List<TEntity> getAll() throws SQLException;
    TEntity get(Integer id) throws SQLException;
}