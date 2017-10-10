package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.IMessage;
import models.MessagesEnvelope;
import models.TextMessage;
import models.internal.AdaptiveCardMessage;
import rx.Single;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.ObjectMapper;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;

public class MessageManager extends BotManagerBase {

    private static final Logger log = Logger.getLogger(MessageManager.class.getName());
    private static final String KIK_URL_STRING = "https://api.kik.com/v1/message";

    public MessageManager(IServletBotConfig config, Gson gson) {
        super(config, gson);
    }

    public void sendTextMessage(String body, String markDownBody, String chatId, String to) throws IOException {
//        sendMessage(TextMessage.CreateTextMessage(chatId, to, body, markDownBody));
        sendMessage(TextMessage.OutgoingTextMessageBuilder.init(chatId)
                .setTo(to)
                .setBody(body)
                .setMarkdownBody(markDownBody)
                .build());
    }

    public void sendAdaptiveCardMessage(JsonObject card, String chatId, String to) throws IOException {
        sendMessage(AdaptiveCardMessage.CreateAdaptiveCardMessage(chatId, to, card));
    }

    public BotTransaction sendMessage(IMessage message) throws IOException {
        return sendMessages(new ArrayList<IMessage>(Collections.singletonList(message)));
    }

    public BotTransaction sendMessages(List<IMessage> messages) throws IOException {

        Gson gson = new GsonBuilder().create();
        JsonElement messagesJson = gson.toJsonTree(MessagesEnvelope.createWithMessages(messages));

        log.info("jsonString: " + messagesJson.toString());

        URL url = new URL(KIK_URL_STRING);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
        addBasicAuth(conn);

        conn.setDoOutput(true);
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            log.warning("caught exception:\n" + e.getMessage());
        }

        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(messagesJson.toString());
        writer.close();

        int respCode = conn.getResponseCode(); // New items get NOT_FOUND on POST
        log.info("responseCode: " + respCode);


//        Unirest.setObjectMapper(new ObjectMapper() {
//            private Gson gson = new Gson();
//
//            public <T> T readValue(String s, Class<T> aClass) {
//                try {
//                    return gson.fromJson(s, aClass);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            public String writeValue(Object o) {
//                try {
//                    return gson.toJson(o);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//
//        int respCode = 400;
//        try {
//            HttpResponse<JsonNode> jsonResponse = Unirest.post(KIK_URL_STRING)
//                    .header("Authorization", basicAuthorizationValue())
//                    .header("Content-Type", "application/json; charset=utf8")
//                    .body(messagesJson)
//                    .asJson();
//
//            respCode = jsonResponse.getStatus();
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }

        return new BotTransaction(respCode);
    }

    public class BotTransaction {

        private int httpResponseCode;

        private BotTransaction(int httpResponseCode) {
            this.httpResponseCode = httpResponseCode;
        }

        public Single<Integer> responseCode() {
            return Single.just(httpResponseCode);
        }
    }
}
