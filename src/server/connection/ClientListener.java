package server.connection;

import main.main.Main;
import msg.ClientMessage;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientListener implements Runnable {
    private Client c;
    boolean listening;
    ObjectInputStream ois;

    public ClientListener(Client c){
        this.c = c;
        listening = true;
        try {
            ois = new ObjectInputStream(c.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(listening){
            try {
                ClientMessage msg = (ClientMessage) ois.readObject();
                Main.getEventLogger().addLowPriorityEntry(msg.toString());
                new Thread(() -> msg.handle()).start();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
