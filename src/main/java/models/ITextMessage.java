package models;

public interface ITextMessage extends IMessage {
    String getBody();

    String getMarkdownBody();
}
