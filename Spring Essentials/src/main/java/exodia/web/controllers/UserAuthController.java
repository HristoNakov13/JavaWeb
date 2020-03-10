package exodia.web.controllers;

import exodia.domain.models.binding.users.UserLoginBindingModel;
import exodia.domain.models.binding.users.UserRegisterBindingModel;
import exodia.domain.models.view.users.UserLoggedViewModel;
import exodia.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserAuthController extends BaseController {
    private UserService userService;

    @Autowired
    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView getRegister(HttpSession session) {
        if (this.isUserLogged(session)) {
            return this.redirect("/home");
        }

        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@ModelAttribute(name = "model") UserRegisterBindingModel model) {
        this.userService.register(model);

        return this.redirect("/login");
    }

    @GetMapping("/login")
    public ModelAndView getLogin(HttpSession session) {
        if (this.isUserLogged(session)) {
            return this.redirect("/home");
        }

        return this.view("login");
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@ModelAttribute(name = "model") UserLoginBindingModel model, @NotNull HttpSession session) {
        UserLoggedViewModel user = this.userService.login(model);
        session.setAttribute("user", user);

        return this.redirect("/home");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        if (this.isUserLogged(session)) {
            session.invalidate();

            return this.view("redirect:/");
        }

        return this.redirect("/login");
    }
}
