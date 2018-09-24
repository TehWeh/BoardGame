package msg.meta;

import connection.ConnectionManager;
import msg.ServerMessage;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class IdInfo extends ServerMessage {

    public IdInfo(int cid, int mid) {
        super(cid, mid);
    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException {

    }

    @Override
    public void handle() {
        ConnectionManager.getManager().setID(this);
    }
}
