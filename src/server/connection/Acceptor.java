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

    static class AcceptorInstance extends Acceptor{
        private ServerSocket servSock;
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

    static class Acceptor2 extends Acceptor{
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;
        public Acceptor2(){
            try {
                InetAddress host = InetAddress.getByName("localhost");
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.bind(new InetSocketAddress(host, 1234));
                serverSocketChannel.register(selector, SelectionKey.
                        OP_ACCEPT);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void startListening() {
            listening = true;
            new Thread(() -> {
                SelectionKey key = null;
                while (true) {
                    try{
                        if (selector.select() <= 0)
                            continue;
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectedKeys.iterator();
                        while (iterator.hasNext()) {
                            key = (SelectionKey) iterator.next();
                            iterator.remove();
                            if (key.isAcceptable()) {
                                SocketChannel sc = serverSocketChannel.accept();
                                sc.configureBlocking(false);
                                sc.register(selector, SelectionKey.
                                        OP_READ);
                                System.out.println("Connection Accepted: "
                                        + sc.getLocalAddress() + "\n");
                            }
                            if (key.isReadable()) {
                                SocketChannel sc = (SocketChannel) key.channel();
                                ByteBuffer bb = ByteBuffer.allocate(1024);
                                sc.read(bb);

                                String result = new String(bb.array()).trim();
                                System.out.println("Message received: "
                                        + result
                                        + " Message length= " + result.length());
                                if (result.length() <= 0) {
                                    sc.close();
                                    System.out.println("Connection closed...");
                                    System.out.println(
                                            "Server will keep running. " +
                                                    "Try running another client to " +
                                                    "re-establish connection");
                                }
                            }
                        }
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }



}
