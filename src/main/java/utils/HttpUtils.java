package utils;

import java.io.InputStream;
import java.util.Scanner;

public class HttpUtils {
    public static String getRequestBody(String requestMethod, InputStream inputStream) {
        if ("POST".equalsIgnoreCase(requestMethod)) {
            Scanner s = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
        return "";
    }
}
