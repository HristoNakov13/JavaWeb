package exodia.web.controllers;

import exodia.domain.models.binding.documents.DocumentScheduleBindingModel;
import exodia.domain.models.view.documents.DocumentDetailsViewModel;
import exodia.domain.models.view.documents.DocumentPrintViewModel;
import exodia.services.DocumentService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class DocumentController extends BaseController {
    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/schedule")
    public ModelAndView getSchedule(HttpSession httpSession) {
        if (!this.isUserLogged(httpSession)) {
            return this.redirect("/login");
        }

        return this.view("schedule");
    }

    @PostMapping("/schedule")
    public ModelAndView postSchedule(@ModelAttribute(name = "model")DocumentScheduleBindingModel model) {
        String documentId = this.documentService.scheduleDocument(model);

        return this.redirect("/details/" + documentId);
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        DocumentDetailsViewModel document = this.documentService.getDetailsDocumentById(id);
        modelAndView.addObject("document", document);

        return this.view("details", modelAndView);
    }

    @GetMapping("/print/{id}")
    public ModelAndView getPrint(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        DocumentPrintViewModel document = this.documentService.getPrintDocumentById(id);
        modelAndView.addObject("document", document);

        return this.view("print", modelAndView);
    }

    @PostMapping("/print/{id}")
    public void postPrint(@PathVariable(name = "id") String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String content = this.documentService.print(id);
        InputStream is = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }
}
