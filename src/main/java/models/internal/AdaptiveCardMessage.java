package models.internal;

import com.google.gson.JsonObject;
import models.Message;

public class AdaptiveCardMessage extends Message {
    public JsonObject card;

    public static AdaptiveCardMessage CreateAdaptiveCardMessage(String chatId, String to, JsonObject cardJson) {
        AdaptiveCardMessage msg = new AdaptiveCardMessage();
        msg.card = cardJson;
        msg.chatId = chatId;
        msg.to = to;
        msg.type = "adaptive-card";
        return msg;
    }
}
