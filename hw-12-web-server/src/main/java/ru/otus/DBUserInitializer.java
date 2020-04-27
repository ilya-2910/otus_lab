package ru.otus;

import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

public class DBUserInitializer {

    private DBServiceUser dbServiceUser;

    public DBUserInitializer(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    public void createAdminUser() {
        User admin = new User(0, "admin");
        admin.setLogin("admin");
        admin.setPassword("admin");
        dbServiceUser.saveUser(admin);
    }

}
