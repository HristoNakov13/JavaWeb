package factory;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.List;
import java.util.Set;

public interface HttpFactory {
    HttpRequest getRequest(List<String> requestLine, List<String> headers, List<String> body);

    HttpResponse getResponse(Set<String> supportedRoutes, HttpRequest request);
}
