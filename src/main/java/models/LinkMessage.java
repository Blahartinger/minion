package models;

public class LinkMessage extends Message {
    public String url;
    public String picUrl;
    public Boolean noForward;
    public String title;
    public String text;
    public String layoutType; // article or overlay
    public LinkMessageAction action;

    public static LinkMessage CreateLinkeMessage(String chatId, String to, String url, String picUrl, String title, String text, boolean noForward) {
        LinkMessage msg = new LinkMessage();
        msg.url = url;
        msg.picUrl = picUrl;
        msg.chatId = chatId;
        msg.to = to;
        msg.type = "link";
        msg.noForward = noForward;
        msg.title = title;
        msg.text = text;
        return msg;
    }

    public class LinkMessageAction {
        public String type; // text or play
        public String text;
    }
}
