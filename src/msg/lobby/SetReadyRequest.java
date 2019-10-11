package msg.lobby;

import msg.ClientMessage;
import server.game.PlayerManager;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SetReadyRequest extends ClientMessage {
    private boolean ready;

    public SetReadyRequest(boolean b){
        ready = b;
    }
    public SetReadyRequest(){}

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(ready);
    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException {
        ready = in.readBoolean();
    }

    @Override
    public void handle() {
        PlayerManager.getManager().setReady(clientID, ready);
    }
}
