package fdmc.web.servlets;

import fdmc.domain.entities.Product;
import fdmc.services.ProductService;
import fdmc.util.HtmlReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products/create")
public class CreateProductServlet extends HttpServlet {
    private final String CREATE_PAGE_FILENAME = "create-product.html";

    private HtmlReader htmlReader;
    private ProductService productService;

    @Inject
    public CreateProductServlet(HtmlReader htmlReader, ProductService productService) {
        this.htmlReader = htmlReader;
        this.productService = productService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String createPage = this.htmlReader.getFileContent(CREATE_PAGE_FILENAME);

        resp.getWriter().println(createPage);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = this.productService.createProduct(req.getParameterMap());

        resp.sendRedirect(String.format("/products/details/%s", product.getName()));
    }
}