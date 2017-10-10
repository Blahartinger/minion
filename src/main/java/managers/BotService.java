package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import managers.internal.AdaptiveCardManager;
import models.TextMessage;
import models.internal.AdaptiveCardMessage;
import serialization.MessageListDeserializer;
import serialization.MessageListSerializer;

public class BotService implements IBotService {
    private IServletBotConfig _config;
    private Gson _gson;
    private MessageManager _messageManager;
    private AdaptiveCardManager _adaptiveCardManager;

    public BotService(IServletBotConfig config) {
        _config = config;
        _gson = new GsonBuilder()
                .registerTypeAdapter(MessageListDeserializer.MESSAGE_LIST_TYPE, new MessageListDeserializer())
                .registerTypeHierarchyAdapter(TextMessage.class, new MessageListSerializer())
                .registerTypeHierarchyAdapter(AdaptiveCardMessage.class, new MessageListSerializer())
                .create();
        _messageManager = new MessageManager(_config, _gson);
        _adaptiveCardManager = new AdaptiveCardManager(_messageManager, _config, _gson);
    }

    @Override
    public MessageManager getMessageManager() {
        return _messageManager;
    }

    @Override
    public AdaptiveCardManager getAdaptiveCardManager() {
        return _adaptiveCardManager;
    }

    @Override
    public Gson getGson() {
        return _gson;
    }
}
