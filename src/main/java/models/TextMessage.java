package models;

import com.google.gson.annotations.SerializedName;

public class TextMessage extends Message {
    public String body;
    @SerializedName("markdown_body")
    public String markdownBody;

//    public static TextMessage CreateTextMessage(String chatId, String to, String body, String markdownBody) {
//        TextMessage msg = new TextMessage();
//        msg.body = body;
//        msg.markdownBody = markdownBody;
//        msg.chatId = chatId;
//        msg.to = to;
//
//        return msg;
//    }

    public static class OutgoingTextMessageBuilder
            extends Message.OutgoingMessageBuilder<OutgoingTextMessageBuilder> {

        protected String body;
        protected String markdownBody;

        public static OutgoingTextMessageBuilder init(String chatId) {
            return new OutgoingTextMessageBuilder(chatId);
        }

        private OutgoingTextMessageBuilder(String chatId) {
            super(chatId);
        }

        public OutgoingTextMessageBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public OutgoingTextMessageBuilder setMarkdownBody(String markdownBody) {
            this.markdownBody = markdownBody;
            return this;
        }

        @Override
        public OutgoingTextMessageBuilder getThis() {
            return this;
        }

        public TextMessage build() {
            return new TextMessage(this);
        }
    }

    // Needed for gson
    public TextMessage() {
    }

    protected TextMessage(OutgoingTextMessageBuilder builder) {
        super(builder);
        this.body = builder.body;
        this.markdownBody = builder.markdownBody;
        this.type = "text";
    }
}
