package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import serialization.MessageListDeserializer;

public class BotService implements IBotService {
    private IServletBotConfig _config;
    private IBotAuthProvider _botAuthProvider;
    private IMessageTransport _messageTransport;
    private Gson _gson;

    private BotService(IServletBotConfig config) {
        _config = config;
    }

    @Override
    public IMessageTransport getMessageTransport() {
        return _messageTransport;
    }

    @Override
    public Gson getGson() {
        return _gson;
    }

    public static class Builder {

        private IServletBotConfig _config;
        private IMessageTransport _messageSender;
        private IBotAuthProvider _botAuthProvider;

        private Builder(IServletBotConfig config) {
            _config = config;
        }

        public static Builder init(IServletBotConfig config) {
            return new Builder(config);
        }

        public Builder setBotAuthProvider(IBotAuthProvider botAuthProvider) {
            _botAuthProvider = botAuthProvider;
            return this;
        }

        public Builder setMessageSender(IMessageTransport messageSender) {
            _messageSender = messageSender;
            return this;
        }

        private Gson createDefaultGsonInstance() {
            return new GsonBuilder()
                    .registerTypeAdapter(MessageListDeserializer.MESSAGE_LIST_TYPE, new MessageListDeserializer())
                    .create();
        }

        public BotService build() {
            BotService service = new BotService(_config);

            Gson gson = createDefaultGsonInstance();
            service._gson = gson;

            if (_botAuthProvider == null) {
                _botAuthProvider = new BotAuthProvider(_config, gson);
            }
            service._botAuthProvider = _botAuthProvider;

            if (_messageSender == null) {
                _messageSender = new BasicMessageTransport(_config, service._botAuthProvider);
            }
            service._messageTransport = _messageSender;

            return service;
        }
    }
}
