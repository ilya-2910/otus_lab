package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.handlers.CreateUserDataRequestHandler;
import ru.otus.core.service.handlers.GetUserDataRequestHandler;
import ru.otus.front.FrontendService;
import ru.otus.front.FrontendServiceImpl;
import ru.otus.front.handlers.CreateUserDataResponseHandler;
import ru.otus.front.handlers.GetUserDataResponseHandler;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.messagesystem.*;

@Configuration
public class App {

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public SessionFactory sessionManagerHibernate() {
        return HibernateUtils.buildSessionFactory("WEB-INF/hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);
    }

    @Bean
    public SessionManagerHibernate sessionManager(SessionFactory sessionFactory) {
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public MessageSystem getMessageSystem() {
        return new MessageSystemImpl();
    }

    @Bean(name = "databaseMsClient")
    public MsClient getDatabaseMsClient(MessageSystem messageSystem,
                                        GetUserDataRequestHandler getUserDataRequestHandler,
                                        CreateUserDataRequestHandler createUserDataRequestHandler) {
        MsClientImpl databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
        //databaseMsClient.addHandler(MessageType.USER_DATA, getUserDataRequestHandler);
        databaseMsClient.addHandler(MessageType.CREATE_USER, createUserDataRequestHandler);
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean(name = "frontendService")
    public FrontendService getFrontendService(MessageSystem messageSystem) {
        MsClientImpl msClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        messageSystem.addClient(msClient);

        FrontendService frontendService = new FrontendServiceImpl(msClient, DATABASE_SERVICE_CLIENT_NAME);
        //msClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
        msClient.addHandler(MessageType.CREATE_USER, new CreateUserDataResponseHandler(frontendService));

        return frontendService;
    }


}
