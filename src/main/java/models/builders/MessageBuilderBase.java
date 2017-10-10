package models.builders;

import models.Message;
import models.SRKeyboard;

import java.util.List;

public class MessageBuilderBase<T extends MessageBuilderBase<T>> {

    private List<SRKeyboard> _keyboards;

    public Message build() {
        return new Message();
    }
}
