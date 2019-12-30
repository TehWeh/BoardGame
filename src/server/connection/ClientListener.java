package server.connection;

import main.main.Main;
import msg.ClientMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientListener implements Runnable {
    private volatile boolean listening;

    private ObjectInputStream[] ins;
    private Thread t;


    public ClientListener(int maxClients){
        ins = new ObjectInputStream[maxClients];
        listening = true;

        Main.getEventLogger().addEntry("New ClientListener starting up");
        t = new Thread(this);
        t.start();
    }

    public void addClient(Client c) throws IOException {
        synchronized (ins){
            ins[c.getID()] = new ObjectInputStream(new BufferedInputStream(c.getSocket().getInputStream()));
        }
    }

    public void removeClient(Client c){
        synchronized (ins){
            ins[c.getID()] = null;
        }
    }

    public void close(){
        try {
            listening = false;
            t.join();
            for(ObjectInputStream in : ins) if(in != null) in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(listening){
            boolean idle = true;
            for(ObjectInputStream in : ins){
                try {
                    if(in == null || in.available() == 0) continue;
                    idle = false;
                    in.readInt();
                    ClientMessage msg = (ClientMessage) in.readObject();
                    Main.getEventLogger().addEntry("Client #" + msg.getClientID() + " -> Server: " + msg.toString());
                    new Thread(() -> msg.handle()).start(); // msg.handle() ?
                } catch (IOException e) {
                    e.printStackTrace();
                    listening = false;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(idle) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else Thread.yield();
        }
    }
}
