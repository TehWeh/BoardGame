package server.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Acceptor {

    private ServerSocket servSock;

    private boolean listening;

    public Acceptor(){
        try {
            servSock = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startListening(){
        listening = true;
        new Thread(new ClientListener()).start();
    }

    class ClientListener implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    Socket s = servSock.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
