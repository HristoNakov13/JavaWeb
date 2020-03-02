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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cats/create")
public class CreateCatServlet extends HttpServlet {
    private final String CREATE_PAGE_PATH = "C:\\Users\\Username\\Desktop\\fdmc\\src\\main\\resources\\views\\create-cat.html";
    private HtmlReader htmlReader;

    @Inject
    public CreateCatServlet(HtmlReader htmlReader) {
        this.htmlReader = htmlReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String createPage = htmlReader.getFileContent(CREATE_PAGE_PATH);

        writer.println(createPage);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cat cat = new Cat();
        cat.setBreed(req.getParameter("breed"));
        cat.setColor(req.getParameter("color"));
        cat.setName(req.getParameter("name"));
        cat.setNumberOfLegs(Integer.parseInt(req.getParameter("numberOfLegs")));

        this.addCatToContext(cat);

        resp.sendRedirect("/cats/profile?catName=" + cat.getName());
    }

    private void addCatToContext(Cat cat) {
        this.getCatsList().add(cat);
    }

    @SuppressWarnings("unchecked")
    private List<Cat> getCatsList() {
        if (this.getServletConfig().getServletContext().getAttribute("kitties") == null) {
            this.getServletConfig().getServletContext().setAttribute("kitties", new ArrayList<Cat>());
        }

        return (List<Cat>) this.getServletConfig().getServletContext().getAttribute("kitties");
    }
}
