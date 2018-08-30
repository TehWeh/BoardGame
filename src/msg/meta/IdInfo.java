package msg.meta;

import connection.ConnectionManager;
import msg.ServerMessage;

public class IdInfo extends ServerMessage {

    public IdInfo(int cid, int mid) {
        super(cid, mid);
    }

    @Override
    public void handle() {
        ConnectionManager.getManager().setID(this);
    }
}
