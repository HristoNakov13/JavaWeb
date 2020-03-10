package exodia.web.controllers;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public abstract class BaseController {
    protected ModelAndView view(String view) {
        return this.view(view, new ModelAndView());
    }

    protected ModelAndView view(String view, ModelAndView modelAndView) {
        modelAndView.setViewName(view);

        return modelAndView;
    }

    protected ModelAndView redirect(String path, ModelAndView modelAndView) {
        return this.view("redirect:" + path, modelAndView);
    }

    protected ModelAndView redirect(String path) {
        return this.view("redirect:" + path);
    }

    protected boolean isUserLogged(HttpSession session) {
        return session.getAttribute("user") != null;
    }
}
