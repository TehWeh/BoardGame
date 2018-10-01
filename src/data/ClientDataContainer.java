package data;

import msg.ClientMessage;
import msg.ClientMessageTask;
import msg.PlayerDataRequest;
import java.util.Timer;


public class ClientDataContainer {
    private static ClientDataContainer container;

    private volatile DataItem<PlayerData> playerdata;

    public static synchronized ClientDataContainer getContainer(){
        if(container == null) container = new ClientDataContainer();
        return container;
    }

    public ClientDataContainer(){
        playerdata = new DataItem<>(500, new PlayerDataRequest());
    }

    public PlayerData getPlayerdata() {
        return playerdata.getData();
    }

    public void setPlayerData(PlayerData data){
        playerdata.receive(data);
    }

    private class DataItem<T extends DataObject>{
        private T content;
        private int inval;
        private ClientMessage msg;

        public DataItem(int inval, ClientMessage m){
            msg = m;
            this.inval = inval;
            new Timer().schedule(new ClientMessageTask(msg), 50);
            content = null;

        }

        public synchronized void receive(T content){
            this.content = content;
            notify();
            new Timer().schedule(new ClientMessageTask(msg), inval);
        }

        public synchronized T getData(){
            try {
                if(content == null) wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return content;
        }
    }
}
