package server.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientManagerFactory {
    private static ClientManager singleton;

    public static ClientManager getSingleton(){
        if(singleton == null) singleton = new ClientManagerInstance();
        return singleton;
    }

    private static class ClientManagerInstance implements ClientManager{
        private int nextID;
        private List<Client> clientList;
        private Map<Integer, Client> clientMap;

        public ClientManagerInstance(){
            clientList = new ArrayList<>();
            clientMap = new HashMap<>();
            nextID = 0;
        }

        public void addClient(Socket s){
            Client c = new Client(nextID, s);
            clientList.add(c);
            clientMap.put(nextID, c);
            nextID++;

        }

        public int getNumberOfClients(){
            return clientList.size();
        }

        public void kickAll(){
            for(Client c : clientList){
                kick(c.getID());
            }
        }

        private void kick(int id){
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
    }
}
