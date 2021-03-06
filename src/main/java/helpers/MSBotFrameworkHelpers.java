package helpers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import experimental.models.AdaptiveCardMessage;
import models.IMessage;
import models.SRKeyboard;
import models.SuggestedResponse;
import models.TextMessage;
import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class MSBotFrameworkHelpers {
    private static final Logger log = Logger.getLogger(MSBotFrameworkHelpers.class.getName());
    private Gson _gson;

    public MSBotFrameworkHelpers(Gson gson) {
        _gson = gson;
    }

    public List<IMessage> convertMSBotFrameworkMessageToMessages(MSBotFrameworkMessage msBotFrameworkMessage, String chatId, String to, boolean sendActionsAsSuggestedResponses) {
        List<IMessage> messagesToSend = new ArrayList<>();

        if (msBotFrameworkMessage.text != null) {
            messagesToSend.add(TextMessage.OutgoingTextMessageBuilder.init(chatId).setTo(to).setBody(msBotFrameworkMessage.text).build());
        }

        for (MSBotFrameworkMessage.MSBotFrameworkMessageAttachment attachment : msBotFrameworkMessage.attachments) {
            if (sendActionsAsSuggestedResponses) {

                JsonObject cardJson = attachment.content;
                JsonArray actionsJsonArray = cardJson.has("actions") ? cardJson.remove("actions").getAsJsonArray() : null;
                List<SuggestedResponse> suggestedResponses = new ArrayList<>();

                JsonArray showCardActions = new JsonArray();
                if (actionsJsonArray != null) {
                    for (JsonElement action : actionsJsonArray) {
                        JsonObject actionJsonObj = action.getAsJsonObject();
                        String actionTypeString = actionJsonObj.get("type").getAsString();
                        if ("Action.ShowCard".equalsIgnoreCase(actionTypeString) || "Action.Submit".equalsIgnoreCase(actionTypeString)) {
                            // Transform action into SR
                            String title = actionJsonObj.get("title").getAsString();
                            JsonObject metadata = null;
                            if (actionJsonObj.has("data")) {
                                metadata = actionJsonObj.get("data").getAsJsonObject();
                            }
                            suggestedResponses.add(SuggestedResponse.createTextSR(title, metadata));
                        } else {
                            showCardActions.add(action);
                        }
                    }
                }

                if (showCardActions.size() > 0) {
                    cardJson.add("actions", showCardActions);
                }

                AdaptiveCardMessage.OutgoingAdaptiveCardMessageBuilder builder =
                        AdaptiveCardMessage.OutgoingAdaptiveCardMessageBuilder.init(chatId)
                                .setTo(to)
                                .setCard(cardJson);

                if (!suggestedResponses.isEmpty()) {
                    builder.setKeyboards(Collections.singletonList(SRKeyboard.createSRKeyboard(suggestedResponses)));
                }

                AdaptiveCardMessage message = builder.build();
                messagesToSend.add(message);
            } else {
                messagesToSend.add(AdaptiveCardMessage.OutgoingAdaptiveCardMessageBuilder.init(chatId)
                        .setTo(to)
                        .setCard(attachment.content).build());
            }
        }

        return messagesToSend;
    }

    public List<IMessage> messagesFromMSBotFrameWorkMessageFromResourceFile(String filename, String chatId, String to, boolean sendActionsAsSuggestedResponses) throws IOException {
        String wildlifeJsonString = FileUtils.resourceFileToString(filename);
        MSBotFrameworkMessage msBotFrameworkMessage = _gson.fromJson(wildlifeJsonString, MSBotFrameworkMessage.class);
        log.info("msBotFrameworkMessage.text: " + msBotFrameworkMessage.text);
        log.info("msBotFrameworkMessage.type: " + msBotFrameworkMessage.type);
        log.info("msBotFrameworkMessage.attachments: " + msBotFrameworkMessage.attachments);
        return convertMSBotFrameworkMessageToMessages(msBotFrameworkMessage, chatId, to, sendActionsAsSuggestedResponses);
    }

    public static class MSBotFrameworkMessage {
        public String type;
        public String inputHint;
        public String speak;
        public String text;
        public String attachmentLayout;
        public List<MSBotFrameworkMessageAttachment> attachments;

        public static class MSBotFrameworkMessageAttachment {
            public String contentType;
            public JsonObject content;
        }
    }
}
