package msg.meta;

import connection.ConnectionManager;
import main.main.Main;
import msg.ServerMessage;

import java.io.*;

public class IdInfo extends ServerMessage {

    public IdInfo(int cid) {
        super(cid);
    }

    public IdInfo(){
        super();
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
        Main.getEventLogger().addEntry("ID received");
    }
}
