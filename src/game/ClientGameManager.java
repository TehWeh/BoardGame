package game;

import config.ConfigurationManager;
import connection.ConnectionManager;
import data.ClientDataContainer;
import data.Player;
import msg.lobby.PlayerRegisterRequest;

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
        return getOwnPlayer() != null;
    }

    public boolean playerReady(){
        Player p = getOwnPlayer();
        if (p == null) return false;
        return p.getReady();
    }

    public Player getOwnPlayer(){
        return ClientDataContainer.getContainer().getData().getPlayer(playerID);
    }
}