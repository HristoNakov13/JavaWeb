package http.request;

import http.HttpCookie;

import java.util.HashMap;
import java.util.Set;

public interface HttpRequest {
    HashMap<String, String> getHeaders();

    HashMap<String, String> getBodyParameters();

    String getMethod();

    void setMethod(String method);

    String getRequestUrl();

    void setRequestUrl(String requestUrl);

    void addHeader(String header, String value);

    void addBodyParameter(String parameter, String value);

    Set<HttpCookie> getCookies();

    void setCookies(Set<HttpCookie> cookies);

    boolean isResource();
}
