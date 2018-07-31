package server.connection;

import java.net.Socket;

public interface ClientManager {
    void addClient(Socket s);
    int getNumberOfClients();
    void kickAll();
}
