package msg;

import connection.ConnectionManager;

import java.io.*;

public abstract class ClientMessage extends Message {
    protected boolean messageRestored;

    public ClientMessage(){
        super();
        this.messageRestored = true;
    }

    public void setID(int id){
        clientID = id;
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
