package models;

public class PictureMessage extends Message implements IPictureMessage {
    protected String picUrl;
    protected String attribution; // gallery or camera

    private PictureMessage(OutgoingPictureMessageBuilder outgoingPictureMessageBuilder) {
        super(outgoingPictureMessageBuilder);
        type = "picture";
        picUrl = outgoingPictureMessageBuilder.picUrl;
        attribution = outgoingPictureMessageBuilder.attribution;
    }

    // Needed for gson
    public PictureMessage() {
    }

    @Override
    public String getPicUrl() {
        return picUrl;
    }

    @Override
    public String getAttribution() {
        return attribution;
    }

    public static class OutgoingPictureMessageBuilder
            extends Message.OutgoingMessageBuilder<OutgoingPictureMessageBuilder> {

        protected String picUrl;
        protected String attribution;

        private OutgoingPictureMessageBuilder(String chatId) {
            super(chatId);
        }

        public static OutgoingPictureMessageBuilder init(String chatId) {
            return new OutgoingPictureMessageBuilder(chatId);
        }

        public OutgoingPictureMessageBuilder setPicUrl(String picUrl) {
            this.picUrl = picUrl;
            return getThis();
        }

        @Override
        public OutgoingPictureMessageBuilder getThis() {
            return this;
        }

        public PictureMessage build() {
            return new PictureMessage(this);
        }
    }
}
