package factory;

import http.request.HttpRequest;
import http.request.HttpRequestImpl;
import http.response.HttpResponse;
import http.response.HttpResponseImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class HttpFactoryImpl implements HttpFactory {
    private final int NOT_FOUND = 404;
    private final int BAD_REQUEST = 400;
    private final int UNAUTHORIZED = 401;
    private final int OK = 200;
    private Map<Integer, String> ERROR_CODE_MESSAGES;
    private Base64.Decoder decoder;

    public HttpFactoryImpl() {
        ERROR_CODE_MESSAGES = new HashMap<>();
        decoder = Base64.getDecoder();
        this.init();
    }

    private void init() {
        ERROR_CODE_MESSAGES.put(NOT_FOUND, "The requested functionality was not found.");
        ERROR_CODE_MESSAGES.put(BAD_REQUEST, "There was an error with the requested functionality due to malformed request.");
        ERROR_CODE_MESSAGES.put(UNAUTHORIZED, "You are not authorized to access the requested functionality.");
    }

    @Override
    public HttpRequest getRequest(List<String> requestLine, List<String> headers, List<String> body) {
        HttpRequest request = new HttpRequestImpl();
        request.setMethod(requestLine.get(0));
        request.setRequestUrl(requestLine.get(1));

        headers.forEach(header -> {
            String[] headData = header.split(": ");
            request.addHeader(headData[0], headData[1]);
        });

        body.stream()
                .filter(line -> !line.isEmpty())
                .forEach(kvp -> {
                    String[] kvpData = kvp.split("=");
                    request.addBodyParameter(kvpData[0], kvpData[1]);
                });

        return request;
    }

    @Override
    public HttpResponse getResponse(Set<String> supportedRoutes, HttpRequest request) {
        String url = request.getRequestUrl();
        String authorizationHeader = request.getHeaders().get("Authorization");
        HashMap<String, String> requestBody = request.getBodyParameters();
        String method = request.getMethod();
        String date = getDate(request.getHeaders().get("Date"));
        String username = null;

        HttpResponse response = new HttpResponseImpl();

        if (!supportedRoutes.contains(url)) {
            response.setStatusCode(NOT_FOUND);
        } else if (authorizationHeader == null) {
            response.setStatusCode(UNAUTHORIZED);
        } else if (requestBody.isEmpty() && method.equals("POST")) {
            response.setStatusCode(BAD_REQUEST);
        } else {
            username = this.decodeUsername(authorizationHeader.split(" ")[1]);
            response.setStatusCode(OK);
            request.getHeaders()
                    .entrySet()
                    .stream()
                    .filter(entry -> !entry.getKey().equals("Authorization"))
                    .forEach(entry -> response.addHeader(entry.getKey(), entry.getValue()));
        }

        byte[] content = this.getBodyContent(requestBody, username, response.getStatusCode());
        response.addHeader("Host", request.getHeaders().get("Host"));
        response.addHeader("Date", date);
        response.setContent(content);

        return response;
    }

    private String getDate(String mapValue) {
        if (mapValue != null) {
            return mapValue;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();

        return dtf.format(localDate);
    }

    private byte[] getBodyContent(Map<String, String> bodyParams, String username, int statusCode) {
        if (this.isErrorCode(statusCode)) {
            return this.ERROR_CODE_MESSAGES.get(statusCode).getBytes();
        }

        String nameParam = bodyParams.get("name");

        String paramsAndValues = bodyParams.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals("name"))
                .map(entry -> String.format("%s - %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));

        String content = String.format("Greetings %s! You have successfully created %s with %s.",
                username,
                nameParam,
                paramsAndValues);

        return content.getBytes();
    }

    private boolean isErrorCode(int statusCode) {
        return statusCode >= 400;
    }

    private String decodeUsername(String encodedUsername) {
        return new String(this.decoder.decode(encodedUsername.getBytes()));
    }
}
