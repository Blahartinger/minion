package models;

public class LinkMessage extends Message implements ILinkMessage {
    public String url;
    public String picUrl;
    public Boolean noForward;
    public String title;
    public String text;
    public String layoutType; // article or overlay
    public LinkMessageAction action;

    public LinkMessage(OutgoingLinkMessageBuilder outgoingLinkMessageBuilder) {
        super(outgoingLinkMessageBuilder);
        url = outgoingLinkMessageBuilder.url;
        picUrl = outgoingLinkMessageBuilder.picUrl;
        noForward = outgoingLinkMessageBuilder.noForward;
        title = outgoingLinkMessageBuilder.title;
        text = outgoingLinkMessageBuilder.text;
        layoutType = outgoingLinkMessageBuilder.layoutType;
        action = outgoingLinkMessageBuilder.action;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getPicUrl() {
        return picUrl;
    }

    @Override
    public Boolean getNoForward() {
        return noForward;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getLayoutType() {
        return layoutType;
    }

    @Override
    public LinkMessageAction getAction() {
        return action;
    }

    public static class OutgoingLinkMessageBuilder
            extends Message.OutgoingMessageBuilder<OutgoingLinkMessageBuilder> {

        protected String url;
        protected String picUrl;
        protected Boolean noForward;
        protected String title;
        protected String text;
        protected String layoutType; // article or overlay
        protected LinkMessageAction action;

        private OutgoingLinkMessageBuilder(String chatId) {
            super(chatId);
        }

        public static OutgoingLinkMessageBuilder init(String chatId) {
            return new OutgoingLinkMessageBuilder(chatId);
        }

        public OutgoingLinkMessageBuilder setUrl(String url) {
            this.url = url;
            return getThis();
        }

        public OutgoingLinkMessageBuilder setPicUrl(String picUrl) {
            this.picUrl = picUrl;
            return getThis();
        }

        public OutgoingLinkMessageBuilder setNoForward(boolean noForward) {
            this.noForward = noForward;
            return getThis();
        }

        public OutgoingLinkMessageBuilder setTitle(String title) {
            this.title = title;
            return getThis();
        }

        public OutgoingLinkMessageBuilder setText(String text) {
            this.text = text;
            return getThis();
        }

        public OutgoingLinkMessageBuilder setLayoutType(String layoutType) {
            this.layoutType = layoutType;
            return getThis();
        }

        public OutgoingLinkMessageBuilder setLinkMessageAction(LinkMessageAction action) {
            this.action = action;
            return getThis();
        }

        @Override
        public OutgoingLinkMessageBuilder getThis() {
            return this;
        }

        public LinkMessage build() {
            return new LinkMessage(this);
        }
    }

    public class LinkMessageAction {
        public String type; // text or play
        public String text;
    }

}
