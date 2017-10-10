package models;

import java.util.List;

public class MSBotFrameworkMessage {
    public String type;
    public String inputHint;
    public String speak;
    public String text;
    public String attachmentLayout;
    public List<MSBotFrameworkMessageAttachment> attachments;
}
