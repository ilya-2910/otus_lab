package ru.otus.servlet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.Optional;


@RestController
public class UserApiServlet {

    private final DBServiceUser dbServiceUser;

    public UserApiServlet(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @PostMapping(value = "api/user", consumes = "application/json", produces = "application/json")
    protected ResponseEntity saveUser(@RequestBody User user) {
        Optional<User> existLogin = dbServiceUser.findByLogin(user.getLogin());
        if (existLogin.isPresent()) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            dbServiceUser.saveUser(user);
            return new ResponseEntity(user, HttpStatus.OK);
        }
    }

}
