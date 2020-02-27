package http.response;

import java.util.HashMap;

public interface HttpResponse {
    HashMap<String, String> getHeaders();

    int getStatusCode();

    void setStatusCode(int statusCode);

    byte[] getContent();

    void setContent(byte[] content);

    byte[] getBytes();

    void addHeader(String header, String value);
}
