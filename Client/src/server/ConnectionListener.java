package server;

import data.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectionListener implements Runnable {

    static int PORT = 1025;

    private static boolean running = false;

    static int conCount = 0;

    static List<Player> plist;

    static Map<Player, Socket> pts;

    static Map<Socket, Player> stp;



    public static void startListening(){
        if(running) throw new IllegalStateException("Already running");
        System.out.println("Starting Connection Listener");
        plist = new ArrayList<>();
        Thread t = new Thread(new ConnectionListener());
        running = true;
    }

    public static String getStatus(){
        if(running){
            return "Server is running on port " + PORT + "\n Currently " + conCount + " Connections to clients";
        }
        else{
            return "No running server detected";
        }
    }


    @Override
    public void run() {
        ServerSocket servsock = null;
        try{
            servsock = new ServerSocket(PORT);
        } catch(IOException e){
            e.printStackTrace();
        }
        while(running){
            try {
                Socket cs = servsock.accept();
                System.out.println("Accepted");
                addNewPlayer(cs);

            } catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    private void addNewPlayer(Socket s){
        Player p = new Player(conCount++);
        pts.put(p, s);
        stp.put(s, p);
    }
}
