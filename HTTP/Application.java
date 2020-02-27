import factory.HttpFactory;
import factory.HttpFactoryImpl;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.*;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Set<String> supportedRoutes = Arrays.stream(scanner.nextLine().split(" ")).collect(Collectors.toSet());
        List<String> requestLine = Arrays.asList(scanner.nextLine().split(" "));
        List<String> headers = new ArrayList<>();

        String line;
        while ((line = scanner.nextLine()).length() > 0) {
            headers.add(line);
        }

        List<String> body = Arrays.asList(scanner.nextLine().split("&"));

        HttpFactory factory = new HttpFactoryImpl();
        HttpRequest request = factory.getRequest(requestLine, headers, body);
        HttpResponse response = factory.getResponse(supportedRoutes, request);

        System.out.println(response.toString());
    }
}
