package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

  List<User> getAllUser();

  Optional<User> findByLogin(String login);

  Optional<User> findRandomUser();

}
