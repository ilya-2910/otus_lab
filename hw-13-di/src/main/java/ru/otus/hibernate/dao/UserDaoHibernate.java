package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.EntityDaoException;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class UserDaoHibernate implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

  @Autowired
  private SessionManagerHibernate sessionManager;

  @Override
  public long create(User user) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Session hibernateSession = currentSession.getHibernateSession();
      if (user.getId() > 0) {
        hibernateSession.merge(user);
      } else {
        hibernateSession.persist(user);
      }
      return user.getId();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new EntityDaoException(e);
    }
  }

  @Override
  public Optional<User> load(long id, Class<User> clazz) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<User> findByLogin(String login) {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Query query = currentSession.getHibernateSession().createQuery("from User where login =: login");
      query.setParameter("login", login);
      List list = query.getResultList();
      if (list == null || list.size() == 0) {
        return Optional.empty();
      } else {
        return Optional.ofNullable((User) list.get(0));
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<User> findRandomUser() {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Query query = currentSession.getHibernateSession().createQuery("from User");
      List list = query.getResultList();
      if (list == null || list.size() == 0) {
        return Optional.empty();
      } else {
        Random r = new Random();
        if (list.size() == 1) {
          return list.stream().findFirst();
        } else {
          return list.stream().skip(r.nextInt(list.size()-1)).findFirst();
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public List<User> findAll() {
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      Query query = currentSession.getHibernateSession().createQuery("from User");
      return query.getResultList();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Collections.emptyList();
  }


  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}
