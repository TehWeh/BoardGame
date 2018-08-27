package server.connection;

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
                Object o = ois.readObject();
                System.out.println(o);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
