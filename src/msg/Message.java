package msg;

import java.io.*;

public abstract class Message implements Serializable {
    protected int clientID;
    protected int messageID;
    protected int threadID;

    public Message() {
        clientID = 0;
        messageID = 0;
    }

    public abstract void writeAdditionalExternal(ObjectOutput out) throws IOException;
    public abstract void readAdditionalExternal(ObjectInput in) throws IOException;

    //@Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(clientID);
        out.writeInt(messageID);
        out.writeInt(threadID);
        writeAdditionalExternal(out);
    }

    public int getClientID(){
        return clientID;
    }
    public int getMessageID(){ return messageID;}

    //@Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        clientID = in.readInt();
        messageID = in.readInt();
        threadID = in.readInt();
        readAdditionalExternal(in);
    }

    public abstract void handle();
}
