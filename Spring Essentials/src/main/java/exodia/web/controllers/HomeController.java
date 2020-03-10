package exodia.web.controllers;

import exodia.domain.models.view.documents.DocumentHomeViewModel;
import exodia.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController extends BaseController {
    private DocumentService documentService;

    @Autowired
    public HomeController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/")
    public ModelAndView index(HttpSession session, ModelAndView modelAndView) {
        if (!this.isUserLogged(session)) {
            return this.view("index");
        }

        List<DocumentHomeViewModel> documents = this.documentService.getAllDocuments();
        modelAndView.addObject("documents", documents);

        return this.view("home", modelAndView);
    }

    @GetMapping("/home")
    public ModelAndView home(HttpSession session) {
        if (!this.isUserLogged(session)) {
            return this.view("index");
        }

        return this.redirect("/");
    }
}