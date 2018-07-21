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



    public void startServer(){
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
}
