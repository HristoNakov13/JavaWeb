package exodia.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {
    protected ModelAndView view(String view) {
        return this.view(view, new ModelAndView());
    }

    protected ModelAndView view(String view, ModelAndView mav) {
        mav.setViewName(view);

        return mav;
    }
}
