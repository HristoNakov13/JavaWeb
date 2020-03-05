package metube.web.servlets;

import metube.domain.dtos.create.TubeCreateDto;
import metube.domain.dtos.view.TubeViewDto;
import metube.domain.entities.Tube;
import metube.services.TubeService;
import metube.util.Constants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tubes/create")
public class TubeCreateServlet extends HttpServlet {
    private TubeService tubeService;

    @Inject
    public TubeCreateServlet(TubeService tubeService) {
        this.tubeService = tubeService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/tube-create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TubeCreateDto tube = (TubeCreateDto) req.getAttribute(Constants.TUBE_CREATE_MODEL_ATTRIBUTE);
        Tube persistedTube = this.tubeService.save(tube);

        resp.sendRedirect("/tubes/details?id=" + persistedTube.getId());
    }
}