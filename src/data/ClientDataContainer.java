package data;

import msg.PlayerDataRequest;


public class ClientDataContainer {
    private static ClientDataContainer container;

    private volatile DataItem<PlayerData> playerdata;

    private static final int PLAYERDATA_REQUEST_INTERVALL = 60_000;

    public static synchronized ClientDataContainer getContainer(){
        if(container == null) container = new ClientDataContainer();
        return container;
    }

    public ClientDataContainer(){
        playerdata = new DataItem<PlayerData>(PLAYERDATA_REQUEST_INTERVALL, new PlayerDataRequest());
    }

    public DataItem<PlayerData> getDataItem(){
        return playerdata;
    }

    public PlayerData getPlayerdata() {
        return playerdata.getData();
    }

    public void setPlayerData(PlayerData data){
        playerdata.receive(data);
    }


}
