package service;

import models.IMessage;

import java.io.IOException;
import java.util.List;

public interface IMessageTransport {
    BasicMessageTransport.BotTransaction sendMessage(IMessage message) throws IOException;

    BasicMessageTransport.BotTransaction sendMessages(List<IMessage> messages) throws IOException;
}
