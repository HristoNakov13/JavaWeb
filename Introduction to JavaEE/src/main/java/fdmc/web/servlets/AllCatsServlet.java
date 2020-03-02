package fdmc.web.servlets;

import fdmc.entities.Cat;
import fdmc.util.HtmlReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/cats/all")
public class AllCatsServlet extends HttpServlet {
    private final String NOT_FOUND_PAGE = "C:\\Users\\Username\\Desktop\\fdmc\\src\\main\\resources\\views\\404-all-cats.html";
    private final String ALL_CATS_PAGE = "C:\\Users\\Username\\Desktop\\fdmc\\src\\main\\resources\\views\\all-cats.html";
    private HtmlReader htmlReader;

    @Inject
    public AllCatsServlet(HtmlReader htmlReader) {
        this.htmlReader = htmlReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cat> cats = this.getAllCats();
        PrintWriter writer = resp.getWriter();

        if (cats.isEmpty()) {
            String notFoundPage = this.htmlReader.getFileContent(NOT_FOUND_PAGE);
            writer.println(notFoundPage);

            return;
        }
        String catsTemplate = this.getAllCatsTemplate(cats);

        String allCatsPage = this.htmlReader
                .getFileContent(ALL_CATS_PAGE)
                .replace("{{allCats}}", catsTemplate);

        writer.println(allCatsPage);
    }

    private String getAllCatsTemplate(List<Cat> cats) {
        return cats.stream()
                .map(cat ->
                        MessageFormat.format("<a href=\"/cats/profile?catName={0}\">{0}</a>",
                                cat.getName()))
                .collect(Collectors.joining("\r\n<br/>\r\n"));
    }

    @SuppressWarnings("unchecked")
    private List<Cat> getAllCats() {
        if (this.getServletContext().getAttribute("kitties") == null) {
            this.getServletContext().setAttribute("kitties", new ArrayList<Cat>());
        }

        return Collections.unmodifiableList((List<Cat>) this.getServletContext().getAttribute("kitties"));
    }
}