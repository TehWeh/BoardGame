package data;

import connection.ConnectionManager;
import msg.PlayerDataRequest;

public class ClientDataContainer {
    private static ClientDataContainer container;

    private volatile PlayerData playerdata;

    public static synchronized ClientDataContainer getContainer(){
        if(container == null) container = new ClientDataContainer();
        return container;
    }

    public ClientDataContainer(){

    }

    public void setPlayerData(PlayerData pd){
        playerdata = pd;
    }

    public PlayerData getPlayerdata() {
        if(playerdata == null) ConnectionManager.getManager().sendMessage(new PlayerDataRequest());
        while(playerdata == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return playerdata; // TODO
    }
}
