package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.EntityDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.Optional;

public class DbServiceDemoHibernate {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemoHibernate.class);

  public static void main(String[] args) {
    // Все главное см в тестах
    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
            User.class, AddressDataSet.class, PhoneDataSet.class);

    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDao = new UserDaoHibernate(sessionManager);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

    User admin = new User(0, "admin");
    admin.setLogin("admin");
    admin.setPassword("admin");
    long id = dbServiceUser.saveUser(admin);

    Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

    id = dbServiceUser.saveUser(new User(1L, "А! Нет. Это же совсем не Вася"));
    Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);

    outputUserOptional("Created user", mayBeCreatedUser);
    outputUserOptional("Updated user", mayBeUpdatedUser);
  }

  private static void outputUserOptional(String header, Optional<User> mayBeUser) {
    System.out.println("-----------------------------------------------------------");
    System.out.println(header);
    mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
  }
}
