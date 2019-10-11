package msg.lobby;

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

    public PlayerRegisterRequest(){}

    @Override
    public void handle() {
        ClientManagerFactory.getSingleton().registerPlayer(clientID, name);
    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
    }
}
