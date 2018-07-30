package server.main;

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
        return 0;
    }

    public static int getNumberOfErrors(){
        return 0;
    }


}
