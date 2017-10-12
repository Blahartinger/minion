package models;

import com.google.gson.JsonElement;

import java.util.List;

public interface IMessage {
    String getChatId();
    String getId();
    String getType();
    String getFrom();
    String getTo();
    String getMention();
    long getDelay();

    JsonElement getMetadata();

    List<SRKeyboard> getKeyboards();

    String getChatType();

    boolean isReadReceiptRequested();

    long getTimestamp();

    List<String> getParticipants();
}
