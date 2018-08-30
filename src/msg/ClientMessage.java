package msg;

import java.io.Serializable;

public abstract class ClientMessage implements Serializable {
    protected int clientID;
    protected int messageID;
    private int threadID;

    public ClientMessage(int clientID, int messageID){
        this.clientID = clientID;
        this.messageID = messageID;
    }

    public int getClientID(){
        return clientID;
    }
    public int getMessageID(){ return messageID;}

    public abstract void handle();
}
