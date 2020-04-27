package ru.otus.servlet;

import com.google.gson.Gson;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class UserApiServlet extends HttpServlet {

    private final DBServiceUser dbServiceUser;

    public UserApiServlet(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user;
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        user = gson.fromJson(reader, User.class);

        Optional<User> existLogin = dbServiceUser.findByLogin(user.getLogin());

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        if (existLogin.isPresent()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson("user already exist"));
        } else {
            dbServiceUser.saveUser(user);
            out.print(gson.toJson(user));
        }
    }

}
