package connection;

import Log.LogSource;
import main.main.Main;
import main.main.WindowManager;
import msg.ClientMessage;
import msg.ServerMessage;
import msg.meta.IdInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionManager implements LogSource {
    private static ConnectionManager singleton;

    public static ConnectionManager getManager(){
        if(singleton == null) singleton = new ConnectionManager();
        return singleton;
    }

    private ConnectionManager(){
        socket = new Socket();
        id = -1;
    }

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private OutputWriter outputWriter;
    private InputListener inputListener;


    private int id;

    public void reset(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setID(IdInfo info){
        this.id = info.getClientID();
        log("ClientManager: ID received (" + id + ")");
    }

    public void connect() throws IOException {
        if (socket.isBound()) throw new IllegalStateException("Already Connected");
        log("ConnectionManager: Setting Up Connection");
        socket.connect(new InetSocketAddress("localhost", 4242));
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        outputWriter = new OutputWriter();
        outputWriter.start();
        inputListener = new InputListener();
        inputListener.start();
        WindowManager.getManager().newWindow();

    }

    @Override
    public void log(String s) {
                Main.getEventLogger().addEntry(s);
    }

    public class OutputWriter extends Thread{
        private Queue<ClientMessage> q;
        public OutputWriter(){
            q = new ArrayBlockingQueue<>(20);
        }
        public void run(){
            while(true){
                synchronized(q) {
                    if (id == -1 || oos == null) {
                        try {
                            q.wait(100);
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ClientMessage m = q.poll();
                    if (m == null) continue;
                    try {
                        oos.writeObject(m);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public class InputListener extends Thread{
        boolean running;


        public void run() {
            running = true;
            log("InputListener starts running");
            while (running) {
                try {
                    ServerMessage m = (ServerMessage) ois.readObject();
                    new Thread(() -> m.handle()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
