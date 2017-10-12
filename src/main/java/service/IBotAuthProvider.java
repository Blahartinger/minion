package service;

import java.net.HttpURLConnection;

public interface IBotAuthProvider {
    void addBasicAuth(HttpURLConnection connection);

    String basicAuthorizationValue();
}
