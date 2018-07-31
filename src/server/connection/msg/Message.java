package server.connection.msg;

public abstract class Message {
    private int clientID;

    public Message(int i){
        clientID = i;
    }

    public int getClientID(){
        return clientID;
    }
}
