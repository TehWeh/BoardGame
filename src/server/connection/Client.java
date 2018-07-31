package server.connection;

import java.net.Socket;

public class Client {
    private int id;
    private Socket socket;

    private ClientState state;

    public Client(int id, Socket socket){
        this.id = id;
        this.socket = socket;

    }

    public int getID(){
        return id;
    }

    public Socket getSocket(){
        return socket;
    }

    public enum ClientState{

    }
}
