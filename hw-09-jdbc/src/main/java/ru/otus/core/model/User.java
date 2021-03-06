package ru.otus.core.model;

import ru.otus.jdbc.dao.Id;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class User {

  @Id
  private Long id;
  private String name;

  public User() {
  }

  public User(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
