package data;

import msg.PlayerDataRequest;


public class ClientDataContainer {
    private static DataContainer<PlayerData> container;


    private static final int PLAYERDATA_REQUEST_INTERVALL = 60_000;

    public static synchronized DataContainer<PlayerData> getContainer(){
        if(container == null) container = new DataContainer(new PlayerDataRequest());
        return container;
    }

}
