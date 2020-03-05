package metube.web.filters;

import metube.domain.dtos.create.TubeCreateDto;
import metube.util.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;

@WebFilter("/tubes/create")
public class CreateTubeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getMethod().toLowerCase().equals("get")) {
            chain.doFilter(req, response);
            return;
        }

        TubeCreateDto tube = new TubeCreateDto();
        tube.setName(req.getParameter("title"));
        tube.setDescription(req.getParameter("description"));
        tube.setYoutubeLink(req.getParameter("youtubeLink"));
        tube.setUploader(req.getParameter("uploader"));

        request.setAttribute(Constants.TUBE_CREATE_MODEL_ATTRIBUTE, tube);

        chain.doFilter(req, response);
    }
}
