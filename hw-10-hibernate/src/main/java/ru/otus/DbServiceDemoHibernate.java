package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.EntityDao;
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
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) {
    // Все главное см в тестах
    SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
            User.class, AddressDataSet.class, PhoneDataSet.class);

    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    EntityDao<User> userDao = new UserDaoHibernate(sessionManager);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

    User вася = new User(0, "Вася");
    AddressDataSet addressDataSet = new AddressDataSet(0l, "пушкина 41");
    addressDataSet.setUser(вася);
    вася.setAddressDataSet(addressDataSet);
    ArrayList<PhoneDataSet> list = new ArrayList<>();
    list.add(new PhoneDataSet(вася, null,"2-12-86-06"));
    list.add(new PhoneDataSet(вася,null, "2-12-86-07"));
    вася.setPhoneDataSet(list);
    long id = dbServiceUser.saveUser(вася);

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
