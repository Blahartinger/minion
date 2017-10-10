package serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.*;
import models.internal.AdaptiveCardMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MessageListDeserializer implements JsonDeserializer<List<IMessage>> {

    private static final Logger log = Logger.getLogger(MessageListDeserializer.class.getName());

    public static Type MESSAGE_LIST_TYPE = new TypeToken<List<IMessage>>() {}.getType();

    private Gson gson = new Gson();

    private Message jsonObjectToMessage(JsonElement jsonElement) {
        JsonElement typeElement = jsonElement.getAsJsonObject().get("type");
        String typeString = typeElement.getAsString();

        log.info("jsonObjectToMessage.type: " + typeString + " " + typeString.getClass().getName());

        if (typeString != null) {
            MessageType messageType = MessageType.fromString(typeString);
            log.info("messageType: " + messageType);
            switch (messageType) {
                case TEXT: {
                    return gson.fromJson(jsonElement, TextMessage.class);
                }
                case START_CHATTING: {
                    return gson.fromJson(jsonElement, StartChattingMessage.class);
                }
                case ADAPTIVE_CARD: {
                    return gson.fromJson(jsonElement, AdaptiveCardMessage.class);
                }
            }
        }
        return null;
    }

    @Override
    public List<IMessage> deserialize(JsonElement jsonElement,
                                      Type type,
                                      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<IMessage> messages = new ArrayList<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement e : jsonArray) {
            Message message = jsonObjectToMessage(e);
            if (message != null) {
                messages.add(message);
            }
        }

        log.info("messages.size: " + messages.size());

        return messages;
    }
}
