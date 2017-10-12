package models;

public interface IVideoMessage {
    String getAttribution();

    String getVideoUrl();

    Boolean getLoop();

    Boolean getMuted();

    Boolean getAutoplay();

    Boolean getNoSave();
}
