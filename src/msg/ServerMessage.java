package msg;

import java.io.Serializable;

public abstract class ServerMessage implements Serializable{
    private int clientID;
    private int messageID;
    private int threadID;


    public ServerMessage(int cid, int mid) {
        clientID = cid;
        messageID = mid;
    }

    public int getClientID(){
        return clientID;
    }
    public int getMessageID(){ return messageID;}

    public abstract void handle();
}
