package msg.meta;

import gui.Alerts;
import javafx.application.Platform;
import msg.ServerMessage;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ServerError extends ServerMessage {
    private String msg;

    public ServerError(){}

    public ServerError(int cid, String s){
        super(cid);
        msg = s;
    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {
        out.writeObject(msg);
    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        msg = (String) in.readObject();
    }

    @Override
    public void handle() {
        Platform.runLater(() -> Alerts.alertError(msg, "Server Error"));
    }
}
