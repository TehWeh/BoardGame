package server.connection;

import msg.ServerMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class ClientManagerFactory {
    private static ClientManager singleton;

    public static ClientManager getSingleton(){
        if(singleton == null) singleton = new ClientManagerInstance();
        return singleton;
    }


}
