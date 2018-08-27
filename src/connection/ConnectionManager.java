package connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionManager {
    private static ConnectionManager singleton;

    public static ConnectionManager getManager(){
        if(singleton == null) singleton = new ConnectionManager();
        return singleton;
    }

    private ConnectionManager(){
        socket = new Socket();
    }

    private Socket socket;

    public void reset(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void connect() throws IOException {
        if (socket.isBound()) throw new IllegalStateException("Already Connected");
        socket.connect(new InetSocketAddress("localhost", 4242));

        new ObjectOutputStream(socket.getOutputStream()).writeObject("Hello World!");
    }
}
