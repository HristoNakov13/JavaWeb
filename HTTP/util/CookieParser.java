package util;

import java.util.Arrays;

public class CookieParser {
    public CookieParser() {
    }

    public String[] parseCookie(String cookieHeader) {
        String[] kvp = cookieHeader
                .replace("Cookie: ", "")
                .split("; ");

        return Arrays.stream(kvp)
                .map(pair -> pair.replace("=", " <-> "))
                .toArray(String[]::new);
    }
}
