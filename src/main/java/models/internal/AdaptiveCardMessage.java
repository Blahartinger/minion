package models.internal;

import com.google.gson.JsonObject;
import models.Message;
import models.TextMessage;

public class AdaptiveCardMessage extends Message {
    public JsonObject card;

    public AdaptiveCardMessage(OutgoingAdaptiveCardMessageBuilder outgoingAdaptiveCardMessageBuilder) {
        super(outgoingAdaptiveCardMessageBuilder);
        type = "adaptive-card";
        card = outgoingAdaptiveCardMessageBuilder.card;
    }

    public static class OutgoingAdaptiveCardMessageBuilder
            extends Message.OutgoingMessageBuilder<OutgoingAdaptiveCardMessageBuilder> {

        protected JsonObject card;

        private OutgoingAdaptiveCardMessageBuilder(String chatId) {
            super(chatId);
        }

        public static OutgoingAdaptiveCardMessageBuilder init(String chatId) {
            return new OutgoingAdaptiveCardMessageBuilder(chatId);
        }

        public OutgoingAdaptiveCardMessageBuilder setCard(JsonObject card) {
            this.card = card;
            return getThis();
        }

        @Override
        public OutgoingAdaptiveCardMessageBuilder getThis() {
            return this;
        }

        public AdaptiveCardMessage build() {
            return new AdaptiveCardMessage(this);
        }
    }
}
