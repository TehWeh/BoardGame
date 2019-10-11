package server.connection;

import main.main.Main;
import msg.ClientMessage;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientListener implements Runnable {
    private Client client;
    private volatile boolean listening;
    ObjectInputStream ois;

    public ClientListener(Client c){
        client = c;
        listening = true;
        try {
            ois = new ObjectInputStream(client.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getEventLogger().addEntry("New ClientListener starting up");
    }

    public void close(){
        try {
            ois.close();
            listening = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(listening){
            try {
                ClientMessage msg = (ClientMessage) ois.readObject();
                Main.getEventLogger().addEntry("Client #" + msg.getClientID() + " -> Server: " + msg.toString());
                new Thread(() -> msg.handle()).start();
            } catch (IOException e) {
                e.printStackTrace();
                listening = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
