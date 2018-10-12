package server.connection;

import msg.ServerMessage;

import java.io.IOException;
import java.net.Socket;

public interface ClientManager {
    void addClient(Socket s) throws IOException;
    int getNumberOfClients();
    void kickAll();

    void sendMessage(ServerMessage m);

    void registerPlayer(int id, String name);
    void unregisterPlayer(int id);
    void removeClient(int id);
}
