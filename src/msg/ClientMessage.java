package msg;

public abstract class ClientMessage extends Message {

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
}
