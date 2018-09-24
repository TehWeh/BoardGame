package msg.meta;

import msg.ClientMessage;
import server.game.PlayerManager;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class NameChangeRequest extends ClientMessage{

    private String name;

    public NameChangeRequest(int clientID, int messageID) {
        super(clientID, messageID);
        this.name = name;
    }

    @Override
    public void handle() {
        PlayerManager.getManager().changeName(clientID, name);
    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException {
        try {
            name = (String) in.readObject();
        } catch (ClassNotFoundException e) {
            messageRestored = false;
            e.printStackTrace();
        }
    }


}
