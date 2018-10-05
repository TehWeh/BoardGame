package msg;

import server.connection.ClientManagerFactory;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DisconnectRequest extends ClientMessage {
    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }

    @Override
    public void handle() {
        ClientManagerFactory.getSingleton().removeClient(clientID);
    }
}
