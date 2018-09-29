package server.connection;

import main.main.Main;
import msg.ServerMessage;
import msg.meta.IdInfo;
import server.game.PlayerManager;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

class ClientManagerInstance implements ClientManager {

    private static final int MAXCLIENTS = 10;
    Client[] clients;
    private ClientWriter clientWriter;
    private int numberOfClients;

    public ClientManagerInstance(){
        clients = new Client[MAXCLIENTS];
        numberOfClients = 0;
    }

    public synchronized int getNextId() {
        for(int i=0; i<MAXCLIENTS; i++) if(clients[i] == null) return i;
        return -1;
    }

    public synchronized void addClient(Socket s) throws IOException, IllegalStateException {
        int id = getNextId();
        if(id == -1) throw new IllegalStateException("No id available");
        Client c  =  new Client(id, s);

        clients[id] = c;
        new Thread(new ClientListener(c)).start();
        clientWriter = new ClientWriter();
        clientWriter.start();
        sendMessage(new IdInfo(c.getID(), -1));

    }

    public int getNumberOfClients(){
        return numberOfClients;
    }

    public void kickAll(){
        for(Client c : clients){
            if(c != null)kick(c.getID());
        }
    }

    @Override
    public synchronized void sendMessage(ServerMessage m) {
        clientWriter.messages.add(m);
    }

    @Override
    public void registerPlayer(int id, String name) {
        if(clients[id] == null) throw new IllegalArgumentException("Id " + id +"  not connected");
        PlayerManager.getManager().addPlayer(id, name);
    }

    private synchronized void kick(int id){
        Client c = clients[id];
        if(c == null) throw new IllegalArgumentException("Client not found");
        try {
            c.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clients[id] = null;
    }

    class ClientWriter extends Thread{
        private Queue<ServerMessage> messages;
        private boolean running;

        public ClientWriter(){
            messages = new ArrayBlockingQueue<>(20);
            running = true;
        }

        public void run() {
            while (running) {
                ServerMessage msg = messages.poll();
                if (msg == null) continue;
                Client c = clients[msg.getClientID()];
                try {
                    c.getObjectOutputStream().writeObject(msg);
                    Main.getEventLogger().addEntry("Server Wrote Message");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}