package http.request;

import http.HttpCookie;

import java.util.HashMap;
import java.util.Set;

public class HttpRequestImpl implements HttpRequest {
    private HashMap<String, String> headers;
    private HashMap<String, String> bodyParameters;
    private String method;
    private String requestUrl;
    private Set<HttpCookie> cookies;

    public HttpRequestImpl() {
        this.headers = new HashMap<>();
        this.bodyParameters = new HashMap<>();
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HashMap<String, String> getBodyParameters() {
        return this.bodyParameters;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public void addBodyParameter(String parameter, String value) {
        this.bodyParameters.put(parameter, value);
    }

    @Override
    public Set<HttpCookie> getCookies() {
        return this.cookies;
    }

    @Override
    public void setCookies(Set<HttpCookie> cookies) {
        this.cookies = cookies;

    }

    @Override
    public boolean isResource() {
        return false;
    }
}
