package serialization;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.IMessage;
import models.TextMessage;
import models.internal.AdaptiveCardMessage;

import java.lang.reflect.Type;
import java.util.List;

public class MessageListSerializer implements JsonSerializer<List<IMessage>> {

    public static Type MESSAGE_LIST_TYPE = new TypeToken<List<IMessage>>() {}.getType();

    private Gson gson = new Gson();

    @Override
    public JsonElement serialize(List<IMessage> iMessages, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonArray jsonArray = new JsonArray();
        for (IMessage message : iMessages) {
            if (message instanceof TextMessage) {
                jsonArray.add(gson.toJson(message, TextMessage.class));
            } else if (message instanceof AdaptiveCardMessage) {
                jsonArray.add(gson.toJson(message, AdaptiveCardMessage.class));
            }
        }

        return jsonArray;
    }
}
