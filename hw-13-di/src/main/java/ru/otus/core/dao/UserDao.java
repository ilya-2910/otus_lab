package ru.otus.core.dao;

import ru.otus.core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends EntityDao<User> {

    Optional<User> findByLogin(String login);
    Optional<User> findRandomUser();
    List<User> findAll();

}
