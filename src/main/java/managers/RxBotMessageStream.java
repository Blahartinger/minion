package managers;

import models.IMessage;
import models.Message;
import models.TextMessage;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class RxBotMessageStream implements IBotMessageStream {

    private class BotResponse {

        private Message _message;

        public BotResponse(Message message) {
            _message = message;
        }

        public Message getResponseMessage() {
            return _message;
        }
    }

    private class BotResponseBuilder {

        public BotResponse evalWithMessage(IMessage message) {
            return new BotResponse(TextMessage.OutgoingTextMessageBuilder.init(message.getChatId())
                    .setTo(message.getFrom())
                    .setBody(String.format("Got %s, Hi from BotResponse!", message.getClass().getSimpleName()))
                    .build());
        }
    }

    private static final Logger log = Logger.getLogger(RxBotMessageStream.class.getName());

    private static final int BOT_MAXIMUM_RESPONSE_TIME_IN_SECONDS = 2;

    private PublishSubject<IMessage> _incomingMessageSteam = PublishSubject.create();
    private PublishSubject<IMessage> _outgoingMessageSteam = PublishSubject.create();

    private Map<Type, BotResponseBuilder> _registeredResponseBuilders = new ConcurrentHashMap<>();

    private IBotService _botService;

    public RxBotMessageStream(IBotService botService) {

        _botService = botService;

        _registeredResponseBuilders.put(TextMessage.class, new BotResponseBuilder());

        _incomingMessageSteam
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<IMessage>() {
                    @Override
                    public void onCompleted() {
                        log.info("incoming.onCompleted()");

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.info("incoming.onError()");
                        /*
                         *  TODO: we want to send back an appropriate error to our users

                         */
                    }

                    @Override
                    public void onNext(IMessage iMessage) {
                        log.info("incoming.onNext()");
                        // TODO: we want to process each new message here...

                        // TODO: send outoging messages like... _outgoingMessageSteam.onNext(messageToSend);
                        // TODO: create another message stream of _possibleAnswers that buffers them until
                        // some deadline. If none are found by the deadline, send one of a set of caned and
                        // generic "I need more time..." responses until we do find an answer.
                        // Could facilitate this by throwing an error and responses from onError(...)

                        // TODO: I want to incorperate message history into formulating responses.
                        // e.g. have it remember the last several requests & our responses (FIFO queue?) for each new interaction


                        Message response = _registeredResponseBuilders.get(iMessage.getClass())
                                .evalWithMessage(iMessage.getClass().cast(iMessage))
                                .getResponseMessage();

                        log.info(String.format("iMessage.class: %s", iMessage.getClass().getSimpleName()));
                        log.info(String.format("response.isNull: %s", response == null ? "YES" : "NO"));

                        _outgoingMessageSteam.onNext(response);
                    }
                });

        _outgoingMessageSteam
                .asObservable()
                .flatMap(new Func1<IMessage, Observable<MessageManager.BotTransaction>>() {
                    @Override
                    public Observable<MessageManager.BotTransaction> call(IMessage message) {
                        log.info("outgoing.flatmap.sendMessages()");
                        try {
                            return Observable.just(_botService.getMessageManager().sendMessage(message));
                        } catch (IOException e) {
                            log.info("outgoing.flatmap.sendMessage::throw::Error");
                            return Observable.error(e);
                        }
                    }
                })
                .subscribe(new Observer<MessageManager.BotTransaction>() {
                    @Override
                    public void onCompleted() {
                        log.info("outgoing.onCompleted()");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.info("outgoing.onError()");
                    }

                    @Override
                    public void onNext(MessageManager.BotTransaction botTransaction) {
                        log.info("outgoing.onNext()");
                        botTransaction.responseCode()
                                .subscribe(new Action1<Integer>() {
                                    @Override
                                    public void call(Integer integer) {
                                        if (integer == 200) {

                                        } else {

                                        }
                                    }
                                });
                    }
                });
    }

    public void registerResponseProvider(BotResponseBuilder botResponseBuilder, Type forType) {
        _registeredResponseBuilders.put(forType.getClass(), botResponseBuilder);
    }

    @Override
    public void feedMessage(IMessage message) {
        _incomingMessageSteam.onNext(message);
    }

    @Override
    public void feedMessages(List<IMessage> incomingMessages) {
        for (IMessage message : incomingMessages) {
            _incomingMessageSteam.onNext(message);
        }
    }
}
