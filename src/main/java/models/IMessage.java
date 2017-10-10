package models;

public interface IMessage {
    String getChatId();
    String getId();
    String getType();
    String getFrom();
    String getTo();
}
