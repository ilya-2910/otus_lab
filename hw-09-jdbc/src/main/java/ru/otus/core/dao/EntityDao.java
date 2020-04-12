package ru.otus.core.dao;

import java.util.Optional;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

public interface EntityDao<T> {

  long create(T objectData);
  Optional<T> load(long id, Class<T> clazz);

  SessionManager getSessionManager();
}
