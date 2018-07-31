package server.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Acceptor {
    public static Acceptor singleton;

    protected boolean listening;
    protected ServerSocket servSock;
    protected static final int PORT = 4242;

    public abstract void startListening();

    public static Acceptor getSingleton(){
        if(singleton == null) singleton = new AcceptorInstance();
        return singleton;
    }

    static class AcceptorInstance extends Acceptor{
        private static final int PORT = 4242;

        public AcceptorInstance(){
            try {
                servSock = new ServerSocket(PORT);
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
                        System.out.println("Accepted");
                        ClientManagerFactory.getSingleton().addClient(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



}
