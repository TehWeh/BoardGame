package game;

import config.ConfigurationManager;
import connection.ConnectionManager;
import data.ClientDataContainer;
import msg.meta.PlayerRegisterRequest;

public class ClientGameManager {
    private static ClientGameManager singleton;

    private int playerID;

    public static synchronized ClientGameManager getManager(){
        if(singleton == null) singleton = new ClientGameManager();
        return singleton;
    }

    public void setPlayerID(int id){
        playerID = id;
    }

    public ClientGameManager(){
        playerID = -1;
    }

    public void register(){
        ConnectionManager.getManager().sendMessage(new PlayerRegisterRequest(ConfigurationManager.getManager().getPlayerName()));

    }

    public boolean registered(){
        return ClientDataContainer.getContainer().getPlayerdata().getPlayer(playerID) != null;
    }
}