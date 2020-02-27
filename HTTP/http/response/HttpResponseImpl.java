package http.response;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HttpResponseImpl implements HttpResponse {
    private final String PROTOCOL = "HTTP/1.1";
    private HashMap<Integer, String> statusCodesStringValues;
    private HashMap<String, String> headers;
    private int statusCode;
    private byte[] content;

    public HttpResponseImpl() {
        this.statusCodesStringValues = new HashMap<>();
        this.headers = new HashMap<>();

        init();
    }

    private void init() {
        this.statusCodesStringValues.put(200, "OK");
        this.statusCodesStringValues.put(400, "Bad Request");
        this.statusCodesStringValues.put(401, "Unauthorized");
        this.statusCodesStringValues.put(404, "Not Found");
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = Arrays.copyOf(content, content.length);
    }

    @Override
    public byte[] getBytes() {
        return this.toString().getBytes();
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public String toString() {
        String responseLine = String.format("%s %s %s",
                PROTOCOL,
                this.getStatusCode(),
                this.statusCodesStringValues.get(this.getStatusCode()));

        String headers = this.getHeaders()
                .entrySet()
                .stream()
                .map(header -> String.format("%s: %s", header.getKey(), header.getValue()))
                .collect(Collectors.joining("\r\n"));

        String body = new String(this.getContent());

        return String.format("%s\r\n%s\r\n%s",
                responseLine,
                headers,
                body);
    }
}
