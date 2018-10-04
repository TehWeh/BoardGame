package game;

import data.ClientDataContainer;

public class ClientGameManager {
    private static ClientGameManager singleton;

    private int playerID;

    public static synchronized ClientGameManager getManager(){
        if(singleton == null) singleton = new ClientGameManager();
        return singleton;
    }

    public ClientGameManager(){
        playerID = -1;
    }

    public void register(int id){
        playerID = id;
    }

    public boolean registered(){
        return ClientDataContainer.getContainer().getPlayerdata().getPlayer(playerID) != null;
    }
}
