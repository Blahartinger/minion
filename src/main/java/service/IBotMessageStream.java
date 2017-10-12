package service;

import models.IMessage;

import java.util.List;

public interface IBotMessageStream {
    void feedMessage(IMessage incomingMessage);
    void feedMessages(List<IMessage> incomingMessages);
}
