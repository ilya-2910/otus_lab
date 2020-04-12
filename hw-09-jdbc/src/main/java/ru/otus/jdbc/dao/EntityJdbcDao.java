package ru.otus.jdbc.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.EntityDao;
import ru.otus.core.dao.EntityDaoException;
import ru.otus.jdbc.DbExecutor;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

public class EntityJdbcDao<T> implements EntityDao<T> {
  private static Logger logger = LoggerFactory.getLogger(EntityJdbcDao.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<T> dbExecutor;
  private final JdbcMapper<T> jdbcMapper = new JdbcMapperImpl();

  public EntityJdbcDao(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
  }

  @Override
  public Optional<T> load(long id, Class<T> clazz) {
    try {
      Query query = jdbcMapper.getSelectQuery(id, clazz);
      return dbExecutor.selectRecord(getConnection(), query.getQuery(), id, resultSet -> {
        try {
          if (resultSet.next()) {
            T  result = jdbcMapper.getEntity(clazz, resultSet);
            return result;
          }
        } catch (SQLException e) {
          logger.error(e.getMessage(), e);
        }
        return null;
      });
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public long create(T objectData) {
    try {
      Query query = jdbcMapper.getInsertQuery(objectData);
      return dbExecutor.insertRecord(getConnection(), query.getQuery(), query.getQueryParams());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new EntityDaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
