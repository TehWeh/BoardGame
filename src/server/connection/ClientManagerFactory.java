package server.connection;

public class ClientManagerFactory {
    private static ClientManager singleton;

    public synchronized static ClientManager getSingleton(){
        if(singleton == null) singleton = new ClientManagerInstance();
        return singleton;
    }


}
