package managers;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class BotManagerBase {
    private static final Logger log = Logger.getLogger(BotManagerBase.class.getName());

    private IServletBotConfig _config;
    private Gson _gson;

    private BotManagerBase() {
    }

    public BotManagerBase(IServletBotConfig config, Gson gson) {
        _config = config;
        _gson = gson;
    }

    public void addBasicAuth(HttpURLConnection connection) {
        connection.setRequestProperty("Authorization", basicAuthorizationValue());
    }

    public String basicAuthorizationValue() {
        String basicAuthVal = null;
        try {
            String authString = _config.authString();
            byte[] bytesEncoded = Base64.encodeBase64(authString.getBytes());
            String authEncoded = new String(bytesEncoded);
            basicAuthVal = "Basic " + authEncoded;
        } catch (IOException e) {
            log.warning(e.getMessage());
        }

        return basicAuthVal;
    }

    public Gson gsonSharedInstance() {
        return _gson;
    }
}
