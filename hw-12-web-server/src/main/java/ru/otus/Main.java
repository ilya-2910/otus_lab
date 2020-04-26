package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

import static ru.otus.server.SecurityType.FILTER_BASED;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {
        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        ru.otus.core.dao.UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User admin = new User(0, "admin");
        admin.setLogin("admin");
        admin.setPassword("admin");
        dbServiceUser.saveUser(admin);

        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(dbServiceUser);
        LoginService loginServiceForBasicSecurity = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                FILTER_BASED,
                userAuthServiceForFilterBasedSecurity,
                loginServiceForBasicSecurity,
                dbServiceUser,
                gson,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
