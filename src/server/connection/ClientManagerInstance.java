package server.connection;

import main.main.Main;
import msg.ServerMessage;
import msg.meta.IdInfo;
import msg.meta.ServerError;
import server.game.PlayerManager;
import util.Utils;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

class ClientManagerInstance implements ClientManager {

    private static final int MAXCLIENTS = 10;
    Client[] clients;
    private ClientWriter clientWriter;
    private int numberOfClients;
    private ClientListener listener;

    public ClientManagerInstance(){
        clients = new Client[MAXCLIENTS];
        numberOfClients = 0;
        listener = new ClientListener(MAXCLIENTS);
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

        clientWriter = new ClientWriter();
        clientWriter.start();
        listener.addClient(c);
        sendMessage(new IdInfo(c.getID()));
    }

    public synchronized void removeClient(int id){
        if(id < 0 || id > MAXCLIENTS || clients[id] == null) throw new IllegalArgumentException("No Client with this id");
        Client c = clients[id];
        clients[id] = null;
        listener.removeClient(c);
        PlayerManager.getManager().removePlayer(id);
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
        clientWriter.addMessage(m);
    }

    public synchronized void sendError(int cid, String s){sendMessage(new ServerError(cid, s));}


    public Client getClient(int id)  {
        if(id < 0 || id > MAXCLIENTS) return null;
        return clients[id];
    }

    @Override
    public void registerPlayer(int id, String name) {
        if(clients[id] == null) throw new IllegalArgumentException("Id " + id +"  not connected");
        PlayerManager pm = PlayerManager.getManager();
        if(pm.isRegistered(id)) sendError(id, "Player #" + id + " is already registered");
        PlayerManager.getManager().addPlayer(id, name);
    }

    @Override
    public void unregisterPlayer(int id) {
        if(getClient(id) == null) sendMessage(new ServerError(id, "No Player with id" + id + "registered"));
        PlayerManager.getManager().removePlayer(id);
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
            messages = new ArrayBlockingQueue<>(40);
            running = true;
        }

        public synchronized void addMessage(ServerMessage msg){
            messages.add(msg);
        }

        public void run() {
            while (running) {
                ServerMessage msg = messages.poll();
                if (msg == null) continue;
                Client c = clients[msg.getClientID()];
                if(c == null){
                    Main.getEventLogger().addEntry("ERROR: Server writes messages to non-existent Client");
                    continue;
                }
                try {
                    c.getObjectOutputStream().writeInt(42);
                    c.getObjectOutputStream().writeObject(msg);
                    c.getObjectOutputStream().flush();
                    c.getObjectOutputStream().reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}