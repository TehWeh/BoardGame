package connection;

import Log.LogSource;
import config.ConfigurationManager;
import game.ClientGameManager;
import main.main.Main;
import msg.ClientMessage;
import msg.ServerMessage;
import msg.meta.IdInfo;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
        ClientGameManager.getManager().setPlayerID(info.getClientID());
        log("Client's Connection Manager: ID received (" + id + ")");
    }

    public void connect() throws IOException {
        if (socket.isBound()) throw new IllegalStateException("Already Connected");
        log("ConnectionManager: Setting Up Connection");
        socket.connect(new InetSocketAddress(ConfigurationManager.getManager().getServerIP(), 4242));
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        outputWriter = new OutputWriter();
        outputWriter.start();
        inputListener = new InputListener();
        inputListener.start();
    }

    public void sendMessage(ClientMessage msg){
        outputWriter.q.add(msg);
    }

    @Override
    public void log(String s) {
                Main.getEventLogger().addEntry(s);
    }

    /**
     *
     * @return true gdw ID erhalten
     */
    public boolean isConntected(){
        return id > -1;
    }

    public class OutputWriter extends Thread{
        private BlockingQueue<ClientMessage> q;
        public OutputWriter(){
            q = new ArrayBlockingQueue<>(30);
        }
        public void run(){
            while(true){
                ClientMessage m = null;
                try {
                    m = q.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                m.setID(id);
                try {
                    oos.writeInt(42);
                    oos.writeObject(m);
                } catch (IOException e) {
                    e.printStackTrace();
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
                    ois.readInt();
                    ServerMessage msg = (ServerMessage) ois.readObject();
                    Main.getEventLogger().addEntry("Server -> Client #" + msg.getClientID() + ": " + msg.toString());
                    new Thread(() -> msg.handle()).start();
                }
                catch(EOFException e){
                    reset();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
