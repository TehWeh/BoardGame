package msg;

import java.io.*;

public abstract class ClientMessage implements Serializable {
    protected int clientID;
    protected int messageID;
    private int threadID;

    protected boolean messageRestored;

    public ClientMessage(int clientID, int messageID){
        this.clientID = clientID;
        this.messageID = messageID;
        this.messageRestored = true;
    }

    public ClientMessage(){
        messageRestored = true;
    }

    public int getClientID(){
        return clientID;
    }
    public int getMessageID(){ return messageID;}

    public abstract void handle();

    public abstract void writeAdditionalExternal(ObjectOutput out) throws IOException;
    public abstract void readAdditionalExternal(ObjectInput in) throws IOException;

    //@Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(clientID);
        out.writeInt(messageID);
        out.writeInt(threadID);

        writeAdditionalExternal(out);

    }

    //@Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        clientID = in.readInt();
        messageID = in.readInt();
        threadID = in.readInt();

        readAdditionalExternal(in);
    }
}
