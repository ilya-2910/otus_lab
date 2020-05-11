package ru.otus.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.List;


@Controller
public class AdminServlet {

    private final DBServiceUser dbServiceUser;

    public AdminServlet(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping({"/admin"})
    public String userListView(Model model) {
        List<User> users = dbServiceUser.getAllUser();
        model.addAttribute("users", users);
        return "admin.html";
    }

}
