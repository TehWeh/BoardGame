package msg;

import data.PlayerData;
import server.connection.ClientManagerFactory;
import server.game.PlayerManager;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PlayerDataRequest extends ClientMessage {
    public PlayerDataRequest() {
    }

    @Override
    public void handle() {
        PlayerData pd = PlayerManager.getManager().getData();
        ClientManagerFactory.getSingleton().sendMessage(new PlayerDataInfo(clientID, pd, 42)); // TODO Message ids
    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException {

    }
}
