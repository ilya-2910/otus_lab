package ru.otus.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.model.User;
import ru.otus.services.UserAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginServlet {

    private final UserAuthService userAuthService;

    public LoginServlet(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/")
    protected String welcome() {
        return "index.html";
    }

    @GetMapping("/login")
    protected String login() {
        return "login.html";
    }

    @PostMapping("/login")
    protected RedirectView auth(@ModelAttribute User user, HttpServletRequest request) {
        if (userAuthService.authenticate(user.getLogin(), user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("isLogged", Boolean.TRUE);
            return new RedirectView("/admin", true);
        }
        return null;
    }

}
