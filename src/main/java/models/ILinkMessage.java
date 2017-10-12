package models;

public interface ILinkMessage {
    String getUrl();

    String getPicUrl();

    Boolean getNoForward();

    String getTitle();

    String getText();

    String getLayoutType();

    LinkMessage.LinkMessageAction getAction();
}
