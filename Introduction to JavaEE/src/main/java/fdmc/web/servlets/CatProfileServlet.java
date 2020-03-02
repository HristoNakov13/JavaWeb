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
import java.util.List;

@WebServlet("/cats/profile")
public class CatProfileServlet extends HttpServlet {
    private HtmlReader htmlReader;
    private final String NOT_FOUND_PAGE_PATH = "C:\\Users\\Username\\Desktop\\fdmc\\src\\main\\resources\\views\\404-cat-profile.html";
    private final String PROFILE_PAGE_PATH = "C:\\Users\\Username\\Desktop\\fdmc\\src\\main\\resources\\views\\cat-profile.html";

    @Inject
    public CatProfileServlet(HtmlReader htmlReader) {
        this.htmlReader = htmlReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String catName = req.getQueryString().split("=")[1];
        Cat cat = this.getCatByName(catName);

        PrintWriter printWriter = resp.getWriter();

        if (cat == null) {
            String notFoundPage = this.htmlReader
                    .getFileContent(NOT_FOUND_PAGE_PATH)
                    .replace("{{catName}}", catName);

            printWriter.println(notFoundPage);

            return;
        }

        String profilePage = this.htmlReader.getFileContent(PROFILE_PAGE_PATH)
                .replace("{{name}}", cat.getName())
                .replace("{{breed}}", cat.getBreed())
                .replace("{{color}}", cat.getColor())
                .replace("{{numberOfLegs}}", String.valueOf(cat.getNumberOfLegs()));

        printWriter.println(profilePage);
    }

    @SuppressWarnings("unchecked")
    private Cat getCatByName(String catName) {
        return ((List<Cat>) (this.getServletContext().getAttribute("kitties")))
                .stream()
                .filter(cat -> cat.getName().equals(catName))
                .findFirst()
                .orElse(null);
    }
}
