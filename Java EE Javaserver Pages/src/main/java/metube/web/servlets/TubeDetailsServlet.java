package metube.web.servlets;

import metube.domain.dtos.view.TubeViewDto;
import metube.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tubes/details")
public class TubeDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TubeViewDto tubeViewDto = (TubeViewDto) req.getAttribute(Constants.TUBE_VIEW_MODEL_ATTRIBUTE);

        req.getRequestDispatcher("/jsps/tube-details.jsp").forward(req, resp);
    }
}