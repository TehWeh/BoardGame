package server.connection;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public abstract class Acceptor {
    public static Acceptor singleton;
    protected boolean listening;
    protected static final int PORT = 4242;

    public abstract void startListening();

    public static Acceptor getSingleton(){
        if(singleton == null) singleton = new AcceptorInstance();
        return singleton;
    }

    static class AcceptorInstance extends Acceptor {
        private ServerSocket servSock;

        public AcceptorInstance() {
            try {
                servSock = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void startListening() {
            listening = true;
            new Thread(new ClientListener()).start();
        }

        class ClientListener implements Runnable {
            @Override
            public void run() {
                while (true) {
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
