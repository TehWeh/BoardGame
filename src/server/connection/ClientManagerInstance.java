package server.connection;

import main.main.Main;
import msg.ServerMessage;
import msg.meta.IdInfo;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

class ClientManagerInstance implements ClientManager {
    private int nextID;
    private List<Client> clientList;
    private Map<Integer, Client> clientMap;
    private ClientWriter clientWriter;


    public ClientManagerInstance(){
        clientList = new ArrayList<>();
        clientMap = new HashMap<>();
        nextID = 0;
    }

    public synchronized void addClient(Socket s) throws IOException {
        Client c = new Client(nextID, s);
        clientList.add(c);
        clientMap.put(nextID, c);
        nextID++;
        new Thread(new ClientListener(c)).start();
        clientWriter = new ClientWriter();
        clientWriter.start();
        sendMessage(new IdInfo(c.getID(), -1));

    }

    public int getNumberOfClients(){
        return clientList.size();
    }

    public void kickAll(){
        for(Client c : clientList){
            kick(c.getID());
        }
    }

    @Override
    public synchronized void sendMessage(ServerMessage m) {
        clientWriter.messages.add(m);
    }

    private synchronized void kick(int id){
        Client c = clientMap.get(id);
        if(c == null) throw new IllegalArgumentException("Client not found");
        try {
            c.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientList.remove(c);
        clientMap.remove(id);
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
                Client c = clientMap.get(msg.getClientID());
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