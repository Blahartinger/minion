package service;

import com.google.gson.Gson;
import models.MessagesEnvelope;

public interface IBotService {
    IMessageTransport getMessageTransport();
    Gson getGson();
    MessagesEnvelope parseMessagesEnvelope(String jsonString);
}
