package models;

import com.google.gson.JsonElement;

import java.util.List;

public class Message implements IMessage{
    public String chatId;
    public String id;
    public String type;
    public String from;
    public String to;
    public JsonElement metadata;
    public List<SRKeyboard> keyboards;

    // Needed for gson
    public Message() {}

    protected Message(Builder builder) {
        chatId  = builder.chatId;
        id = builder.id;
        type = builder.type;
        metadata = builder.metadata;
        keyboards = builder.keyboards;
    }

    protected Message(OutgoingMessageBuilder outgoingMessageBuilder) {
        this((Builder)outgoingMessageBuilder);
        to = outgoingMessageBuilder.to;

    }

    public Message(IncomingMessageBuilder incomingMessageBuilder) {
        this((Builder)incomingMessageBuilder);
        from = incomingMessageBuilder.from;
    }

    @Override
    public String getChatId() {
        return chatId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getTo() {
        return to;
    }

    public static abstract class Builder<T extends Builder<T>> {
        protected String chatId;
        protected String id;
        protected String type;
        protected JsonElement metadata;
        protected List<SRKeyboard> keyboards;

        public Builder(String chatId) {
            this.chatId = chatId;
        }

        public T setId(String id) {
            this.id = id;
            return getThis();
        }

        public T setType(String type) {
            this.type = type;
            return getThis();
        }

        public T setMetadata(JsonElement metadata) {
            this.metadata = metadata;
            return getThis();
        }

        public T setKeyboards(List<SRKeyboard> keyboards) {
            this.keyboards = keyboards;
            return getThis();
        }

        public abstract T getThis();
    }

    public static abstract class OutgoingMessageBuilder<T extends OutgoingMessageBuilder<T>>
            extends Message.Builder<OutgoingMessageBuilder<T>> {

        protected String to;

        public OutgoingMessageBuilder(String chatId) {
            super(chatId);
        }

        public T setTo(String to) {
            this.to = to;
            return getThis();
        }

        @Override
        public abstract T getThis();
    }

    public static abstract class IncomingMessageBuilder<T extends IncomingMessageBuilder<T>>
            extends Message.Builder<IncomingMessageBuilder<T>> {

        protected String from;

        public IncomingMessageBuilder(String chatId) {
            super(chatId);
        }

        public T setTo(String from) {
            this.from = from;
            return getThis();
        }


        @Override
        public abstract T getThis();
    }
}
