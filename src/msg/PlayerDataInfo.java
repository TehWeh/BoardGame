package msg;

import data.ClientDataContainer;
import data.PlayerData;
import main.main.Main;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PlayerDataInfo extends ServerMessage    {

    public PlayerData data;

    public PlayerDataInfo(int cid, PlayerData data) {
        super(cid);
        this.data = data;
        //System.out.println("Message contains " + data.playerCount + " Players");
        //data.playerCount = 12;
    }

    public PlayerDataInfo(){}

    @Override
    public void handle(){
        ClientDataContainer.getContainer().receive(data);
        // Main.getEventLogger().addEntry("Client received " + data.playerCount + " Players, magic = " + magic + "Magic2 = " + data.magic);
    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {
        out.writeObject(data);
    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException {
        try {
            data = (PlayerData) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
