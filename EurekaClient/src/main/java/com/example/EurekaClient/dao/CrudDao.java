package com.example.EurekaClient.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<TEntity> {
    TEntity add(TEntity entity) throws SQLException;
    void delete(Integer id) throws SQLException;
    void update(Integer id, TEntity updatedEntity) throws SQLException;

    List<TEntity> getAll() throws SQLException;
    TEntity getById(Integer id) throws SQLException;
}