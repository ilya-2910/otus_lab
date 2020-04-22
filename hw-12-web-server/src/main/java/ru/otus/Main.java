package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.helpers.FileSystemHelper;
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

        UserDao userDao = new InMemoryUserDao();
        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(userDao);
        LoginService loginServiceForBasicSecurity = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        //LoginService loginServiceForBasicSecurity = new InMemoryLoginServiceImpl(userDao);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                FILTER_BASED,
                userAuthServiceForFilterBasedSecurity,
                loginServiceForBasicSecurity,
                userDao,
                gson,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
