package ru.otus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.EntityDao;
import ru.otus.core.model.Account;
import ru.otus.core.service.DBServiceAccount;
import ru.otus.core.service.DbServiceAccountImpl;
import ru.otus.jdbc.dao.EntityJdbcDao;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.jdbc.DbExecutor;
import ru.otus.h2.DataSourceH2;
import ru.otus.core.model.User;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) throws Exception {
    doUser();
    doAccount();
  }

  private static void doUser() throws SQLException {
    DataSource dataSource = new DataSourceH2();
    DbServiceDemo demo = new DbServiceDemo();

    demo.createTable(dataSource);

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor dbExecutor = new DbExecutor();
    EntityDao<User> entityDao = new EntityJdbcDao(sessionManager, dbExecutor);

    DBServiceUser dbServiceUser = new DbServiceUserImpl(entityDao);
    long id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
    Optional<User> user = dbServiceUser.getUser(id);

    System.out.println(user);
    user.ifPresentOrElse(
            crUser -> logger.info("created user, name:{}", crUser.getName()),
            () -> logger.info("user was not created")
    );
  }

  public static void doAccount() throws Exception {
    DataSource dataSource = new DataSourceH2();
    DbServiceDemo demo = new DbServiceDemo();

    demo.createAccountTable(dataSource);

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor dbExecutor = new DbExecutor();
    EntityDao<Account> entityDao = new EntityJdbcDao(sessionManager, dbExecutor);

    DBServiceAccount dbServiceUser = new DbServiceAccountImpl(entityDao);
    long id = dbServiceUser.saveAccount(new Account(0l, "type", 2));
    Optional<Account> account = dbServiceUser.getAccount(id);

    System.out.println(account);
    account.ifPresentOrElse(
            crUser -> logger.info("created account, type:{}", crUser.getType()),
            () -> logger.info("account was not created")
    );

  }

  private void createTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }

  private void createAccountTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table account(no long auto_increment, type varchar(50), rest integer)")) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }

}
