package msg;

import data.ClientDataContainer;
import data.PlayerData;
import main.main.Main;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PlayerDataInfo extends ServerMessage {

    PlayerData data;

    public PlayerDataInfo(int cid, int mid, PlayerData data) {
        super(cid, mid);
        this.data = data;
    }

    @Override
    public void handle(){
        ClientDataContainer.getContainer().setPlayerData(data);
    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException {

    }
}
