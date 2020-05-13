package ru.otus.core.dao;

public class EntityDaoException extends RuntimeException {
  public EntityDaoException(Exception ex) {
    super(ex);
  }
}
