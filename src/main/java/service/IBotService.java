package service;

import com.google.gson.Gson;

public interface IBotService {
    IMessageTransport getMessageTransport();
    Gson getGson();
}
