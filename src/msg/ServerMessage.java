package msg;


public abstract class ServerMessage extends Message{

    public ServerMessage(int id) {
        clientID = id;
    }
    public ServerMessage(){}
}

