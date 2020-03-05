package metube.web.servlets;


import metube.domain.dtos.view.AllTubesViewDto;
import metube.services.TubeService;
import metube.util.Constants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tubes/all")
public class TubeAllServlet extends HttpServlet {
    private TubeService tubeService;

    @Inject
    public TubeAllServlet(TubeService tubeService) {
        this.tubeService = tubeService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AllTubesViewDto> allTubes = this.tubeService.getAllTubes();
        req.setAttribute(Constants.ALL_TUBES_VIEW_MODEL_ATTRIBUTE, allTubes);

        req.getRequestDispatcher("/jsps/tubes-all.jsp").forward(req, resp);
    }
}
