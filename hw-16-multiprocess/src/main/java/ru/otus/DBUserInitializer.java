package ru.otus;

import org.springframework.stereotype.Component;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import javax.annotation.PostConstruct;

//@Component
public class DBUserInitializer {

    private DBServiceUser dbServiceUser;

    public DBUserInitializer(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @PostConstruct
    public void createAdminUser() {
        User admin = new User(0, "admin");
        admin.setLogin("admin");
        admin.setPassword("admin");
        dbServiceUser.saveUser(admin);
    }

}
