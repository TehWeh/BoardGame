package server.main;

import server.connection.Acceptor;
import server.connection.ClientManager;
import server.connection.ClientManagerFactory;

public class Server {
    private static Server server;

    private String name;
    private ServerState status;

    public Server(String name){
        this.name = name;
        status = ServerState.STARTED;
        status = ServerState.ACCEPTING;
    }



    public static void startServer() throws IllegalStateException{
        if(server != null) throw new IllegalStateException("Server is already active");
        server = new Server("Public Server");
        Acceptor.getSingleton().startListening();
    }

    public void startGame(){
        status = ServerState.RUNNING;
    }

    public void stopServer(){
        server = null;
    }

    public static boolean isActive(){
        return server != null;
    }

    public static int getNumberOfPlayers(){
        return ClientManagerFactory.getSingleton().getNumberOfClients();
    }

    public static int getNumberOfErrors(){
        return 0;
    }

    public static String getStatus(){
        return server.status.toString();
    }

    public void kickAll(){
        ClientManagerFactory.getSingleton().kickAll();
    }

    public static Server getSingleton(){
        return server;
    }


}
