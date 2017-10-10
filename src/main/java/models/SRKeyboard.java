package models;

import java.util.List;

public class SRKeyboard {
    public String type = "suggested";
    public List<SuggestedResponse> responses;

    public static SRKeyboard createSRKeyboard(List<SuggestedResponse> suggestedResponses) {
        SRKeyboard srKeyboard = new SRKeyboard();
        srKeyboard.responses = suggestedResponses;
        return srKeyboard;
    }
}
