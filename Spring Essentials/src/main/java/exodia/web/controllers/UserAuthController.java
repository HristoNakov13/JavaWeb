package exodia.web.controllers;

import exodia.domain.models.binding.UserLoginBindingModel;
import exodia.domain.models.binding.UserRegisterBindingModel;
import exodia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAuthController extends BaseController{
    private UserService userService;

    @Autowired
    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView getRegister() {

        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView postRegister(@ModelAttribute(name = "model") UserRegisterBindingModel model) {
        try {
            this.userService.register(model);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            return this.view("register");
        }

        return this.view("home");
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return this.view("login");
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@ModelAttribute(name = "model")UserLoginBindingModel model) {
        return this.view("home");
    }
}
