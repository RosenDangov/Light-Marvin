import com.google.common.util.concurrent.FutureCallback;
import command.DeleteOnReactionListener;
import command.PartyListener;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LightBot {

    public LightBot(String token) throws IOException {
        DiscordAPI api = Javacord.getApi(token, true);
        // connect
        api.connect(new FutureCallback<DiscordAPI>() {
            public void onSuccess(DiscordAPI api) {
                // register listener
                api.registerListener(new PartyListener());
                api.registerListener(new DeleteOnReactionListener());
            }
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void main (String[] args) throws IOException {
        LightBot lb = new LightBot("Mzc3ODEyOTQ1MDA4MDY2NTYw.DOS90w.Z4IlejhubPAZ8g8Gi-xjwlHt4eo");
    }



}
