package ru.otus.jdbc.dao;

import java.sql.ResultSet;

public interface JdbcMapper<T> {

    Query getSelectQuery(long id, Class<T> userClass);
    Query getInsertQuery(T entity);

    T getEntity(Class<T> clazz, ResultSet resultSet);
}
