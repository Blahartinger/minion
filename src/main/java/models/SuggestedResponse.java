package models;

import com.google.gson.JsonObject;

public class SuggestedResponse {
    public String type;
    public String body;
    public JsonObject metadata;

    public static SuggestedResponse createTextSR(String body) {
        return createTextSR(body, null);
    }

    public static SuggestedResponse createTextSR(String body, JsonObject metadata)
     {
        SuggestedResponse suggestedResponse = new SuggestedResponse();
        suggestedResponse.type = "text";
        suggestedResponse.body = body;
        suggestedResponse.metadata = metadata;
        return suggestedResponse;
    }
}
