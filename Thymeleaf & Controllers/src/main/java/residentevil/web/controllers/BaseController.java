package residentevil.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    protected ModelAndView view(String view, ModelAndView modelAndView) {
        modelAndView.setViewName(view);

        return modelAndView;
    }

    protected ModelAndView view(String view) {
        return this.view(view, new ModelAndView());
    }

    protected ModelAndView redirect(String path) {
        return this.redirect(path, new ModelAndView());
    }

    protected ModelAndView redirect(String path, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:" + path);

        return modelAndView;
    }
}
