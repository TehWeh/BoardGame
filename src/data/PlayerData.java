package data;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PlayerData extends DataObject {
    public int playerCount;
    private Player[] players;

    public PlayerData(){}

    public PlayerData(int maxs){
        players = new Player[maxs];
    }

    public int getPlayerCount(){
        return playerCount;
    }



    public Player[] getPlayers(){
        return players;
    }

    public Player getPlayer(int i){
        return players[i];
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(playerCount);
        for(int i=0; i<playerCount; i++) out.writeObject(players[i]);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        playerCount = in.readInt();
        players = new Player[playerCount];
        for(int i=0; i<playerCount; i++) players[i] = (Player) in.readObject();
    }
}
