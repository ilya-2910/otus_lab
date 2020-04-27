package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.EntityDao;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceAccountImpl implements DBServiceAccount {
  private static Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

  private final EntityDao<Account> entityDao;

  public DbServiceAccountImpl(EntityDao<Account> entityDao) {
    this.entityDao = entityDao;
  }

  @Override
  public long saveAccount(Account user) {
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
  public Optional<Account> getAccount(long id) {
    try (SessionManager sessionManager = entityDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<Account> userOptional = entityDao.load(id, Account.class);

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
