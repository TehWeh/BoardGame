package msg.meta;

import msg.ClientMessage;
import server.connection.ClientManagerFactory;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PlayerRegisterRequest extends ClientMessage {

    private String name;

    public PlayerRegisterRequest(String name){
        this.name = name;
    }

    @Override
    public void handle() {
        ClientManagerFactory.getSingleton().registerPlayer(clientID, name);
    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException {

    }
}
