package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessagesEnvelope {
    public List<IMessage> messages;

    public static MessagesEnvelope createWithMessages(List<IMessage> messages) {
        MessagesEnvelope msgs = new MessagesEnvelope();
        msgs.messages = messages;
        return msgs;
    }

    public static MessagesEnvelope createWithMessage(IMessage message) {
        return createWithMessages(new ArrayList<>(Collections.singleton(message)));
    }
}
