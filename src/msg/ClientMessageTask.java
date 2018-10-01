package msg;

import connection.ConnectionManager;

import java.util.TimerTask;

public class ClientMessageTask extends TimerTask {

    private ClientMessage msg;
    public ClientMessageTask(ClientMessage msg){
        this.msg = msg;
    }

    @Override
    public void run() {
        ConnectionManager.getManager().sendMessage(msg);
    }
}
