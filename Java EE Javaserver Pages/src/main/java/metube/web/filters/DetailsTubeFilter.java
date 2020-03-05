package metube.web.filters;

import metube.domain.dtos.view.TubeViewDto;
import metube.services.TubeService;
import metube.util.Constants;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/tubes/details")
public class DetailsTubeFilter implements Filter {
    private TubeService tubeService;

    @Inject
    public DetailsTubeFilter(TubeService tubeService) {
        this.tubeService = tubeService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tubeId = request.getParameter("id");
        TubeViewDto tubeViewDto = this.tubeService.findById(tubeId);

        if (tubeViewDto == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/404-tube.jsp");
            return;
        }

        request.setAttribute(Constants.TUBE_VIEW_MODEL_ATTRIBUTE, tubeViewDto);

        chain.doFilter(request, response);
    }
}