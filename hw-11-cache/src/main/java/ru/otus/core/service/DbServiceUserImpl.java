package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.Cacheble;
import ru.otus.core.dao.EntityDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final EntityDao<User> entityDao;

  public DbServiceUserImpl(EntityDao<User> entityDao) {
    this.entityDao = entityDao;
  }

  @Override
  public long saveUser(User user) {
    try (SessionManager sessionManager = entityDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = entityDao.create(user);
        sessionManager.commitSession();

        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  @Cacheble
  public Optional<User> getUser(long id) {
    try (SessionManager sessionManager = entityDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = entityDao.load(id, User.class);

        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

}
