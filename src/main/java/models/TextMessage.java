package models;

import com.google.gson.annotations.SerializedName;

public class TextMessage extends Message implements ITextMessage {
    protected String body;
    @SerializedName("markdown_body")
    protected String markdownBody;
    protected Long typeTime;

    // Needed for gson
    public TextMessage() {
    }

    protected TextMessage(OutgoingTextMessageBuilder builder) {
        super(builder);
        this.body = builder.body;
        this.markdownBody = builder.markdownBody;
        this.type = "text";
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getMarkdownBody() {
        return markdownBody;
    }

    public static class OutgoingTextMessageBuilder
            extends Message.OutgoingMessageBuilder<OutgoingTextMessageBuilder> {

        protected String body;
        protected String markdownBody;

        private OutgoingTextMessageBuilder(String chatId) {
            super(chatId);
        }

        public static OutgoingTextMessageBuilder init(String chatId) {
            return new OutgoingTextMessageBuilder(chatId);
        }

        public OutgoingTextMessageBuilder setBody(String body) {
            this.body = body;
            return getThis();
        }

        public OutgoingTextMessageBuilder setMarkdownBody(String markdownBody) {
            this.markdownBody = markdownBody;
            return getThis();
        }

        @Override
        public OutgoingTextMessageBuilder getThis() {
            return this;
        }

        public TextMessage build() {
            return new TextMessage(this);
        }
    }


}
