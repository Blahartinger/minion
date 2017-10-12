package models;

public class VideoMessage extends Message implements IVideoMessage {
    protected String attribution; // gallery or camera
    protected String videoUrl;
    protected Boolean loop;
    protected Boolean muted;
    protected Boolean autoplay;
    protected Boolean noSave;

    // Needed for gson
    public VideoMessage() {

    }

    private VideoMessage(OutgoingVideoMessageBuilder outgoingVideoMessageBuilder) {
        super(outgoingVideoMessageBuilder);
        type = "video";
        attribution = outgoingVideoMessageBuilder.attribution;
        videoUrl = outgoingVideoMessageBuilder.videoUrl;
        loop = outgoingVideoMessageBuilder.loop;
        muted = outgoingVideoMessageBuilder.muted;
        autoplay = outgoingVideoMessageBuilder.autoplay;
        noSave = outgoingVideoMessageBuilder.noSave;
    }

    @Override
    public String getAttribution() {
        return attribution;
    }

    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public Boolean getLoop() {
        return loop;
    }

    @Override
    public Boolean getMuted() {
        return muted;
    }

    @Override
    public Boolean getAutoplay() {
        return autoplay;
    }

    @Override
    public Boolean getNoSave() {
        return noSave;
    }

    public static class OutgoingVideoMessageBuilder
            extends Message.OutgoingMessageBuilder<OutgoingVideoMessageBuilder> {

        protected String attribution;
        protected String videoUrl;
        protected Boolean loop;
        protected Boolean muted;
        protected Boolean autoplay;
        protected Boolean noSave;

        private OutgoingVideoMessageBuilder(String chatId) {
            super(chatId);
        }

        public static OutgoingVideoMessageBuilder init(String chatId) {
            return new OutgoingVideoMessageBuilder(chatId);
        }

        public OutgoingVideoMessageBuilder setAttribution(String attribution) {
            this.attribution = attribution;
            return getThis();
        }

        public OutgoingVideoMessageBuilder setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return getThis();
        }

        public OutgoingVideoMessageBuilder setLoop(boolean loop) {
            this.loop = loop;
            return getThis();
        }

        public OutgoingVideoMessageBuilder setMuted(boolean muted) {
            this.muted = muted;
            return getThis();
        }

        public OutgoingVideoMessageBuilder setAutoplay(boolean autoplay) {
            this.autoplay = autoplay;
            return getThis();
        }

        public OutgoingVideoMessageBuilder setNosave(boolean noSave) {
            this.noSave = noSave;
            return getThis();
        }

        @Override
        public OutgoingVideoMessageBuilder getThis() {
            return this;
        }

        public VideoMessage build() {
            return new VideoMessage(this);
        }
    }
}
