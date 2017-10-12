package models;

import com.google.gson.JsonElement;

import java.util.List;

public class Message implements IMessage {
    protected String chatId;
    protected String id;
    protected String type;
    protected String from;
    protected String to;
    protected JsonElement metadata;
    protected List<SRKeyboard> keyboards;
    protected String mention;
    protected long delay;
    protected String chatType;
    protected boolean readReceiptRequested;
    protected long timestamp;
    protected List<String> participants;

    // Needed for gson
    public Message() {
    }

    protected Message(Builder builder) {
        chatId = builder.chatId;
        id = builder.id;
        type = builder.type;
        metadata = builder.metadata;
        keyboards = builder.keyboards;
        mention = builder.mention;
    }

    protected Message(OutgoingMessageBuilder outgoingMessageBuilder) {
        this((Builder) outgoingMessageBuilder);
        to = outgoingMessageBuilder.to;
        delay = outgoingMessageBuilder.delay;
    }

    protected Message(IncomingMessageBuilder incomingMessageBuilder) {
        this((Builder) incomingMessageBuilder);
        from = incomingMessageBuilder.from;
        chatType = incomingMessageBuilder.chatType;
        readReceiptRequested = incomingMessageBuilder.readReceiptRequested;
        timestamp = incomingMessageBuilder.timestamp;
        participants = incomingMessageBuilder.participants;
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

    @Override
    public String getMention() {
        return mention;
    }

    @Override
    public long getDelay() {
        return delay;
    }

    @Override
    public JsonElement getMetadata() {
        return metadata;
    }

    @Override
    public List<SRKeyboard> getKeyboards() {
        return keyboards;
    }

    @Override
    public String getChatType() {
        return chatType;
    }

    @Override
    public boolean isReadReceiptRequested() {
        return readReceiptRequested;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public List<String> getParticipants() {
        return participants;
    }

    public static abstract class Builder<T extends Builder<T>> {
        protected String chatId;
        protected String id;
        protected String type;
        protected JsonElement metadata;
        protected List<SRKeyboard> keyboards;
        protected String mention;

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

        public T setMention(String mention) {
            this.mention = mention;
            return getThis();
        }

        public abstract T getThis();
    }

    public static abstract class OutgoingMessageBuilder<T extends OutgoingMessageBuilder<T>>
            extends Message.Builder<OutgoingMessageBuilder<T>> {

        protected String to;
        protected long delay;

        public OutgoingMessageBuilder(String chatId) {
            super(chatId);
        }

        public T setTo(String to) {
            this.to = to;
            return getThis();
        }

        public T setDelay(long delay) {
            this.delay = delay;
            return getThis();
        }

        @Override
        public abstract T getThis();
    }

    public static abstract class IncomingMessageBuilder<T extends IncomingMessageBuilder<T>>
            extends Message.Builder<IncomingMessageBuilder<T>> {

        protected String from;
        protected String chatType;
        protected boolean readReceiptRequested;
        protected long timestamp;
        protected List<String> participants;

        public IncomingMessageBuilder(String chatId) {
            super(chatId);
        }

        public T setTo(String from) {
            this.from = from;
            return getThis();
        }

        public T setChatType(String chatType) {
            this.chatType = chatType;
            return getThis();
        }

        public T setReadRecieptRequested(boolean readReceiptRequested) {
            this.readReceiptRequested = readReceiptRequested;
            return getThis();
        }

        public T setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return getThis();
        }

        public T setParticipants(List<String> participants) {
            this.participants = participants;
            return getThis();
        }

        @Override
        public abstract T getThis();
    }
}
