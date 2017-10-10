package managers;

import com.google.gson.Gson;
import managers.internal.AdaptiveCardManager;

public interface IBotService {
    MessageManager getMessageManager();

    AdaptiveCardManager getAdaptiveCardManager();

    Gson getGson();
}
