package msg.meta;

import msg.ClientMessage;
import server.game.PlayerManager;

public class NameChangeRequest extends ClientMessage {

    private String name;

    public NameChangeRequest(int clientID, int messageID) {
        super(clientID, messageID);
        this.name = name;
    }

    @Override
    public void handle() {
        PlayerManager.getManager().changeName(clientID, name);
    }
}
