package server.connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private int id;
    private Socket socket;

    public ObjectOutputStream getObjectOutputStream() {
        return oos;
    }
    private ObjectOutputStream oos;
    private ClientState state;


    Client(int id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        state = ClientState.CONNECTED;
    }

    public int getID(){
        return id;
    }

    public Socket getSocket(){
        return socket;
    }

    public enum ClientState{
        CONNECTED, REGISTERED;
    }
}
