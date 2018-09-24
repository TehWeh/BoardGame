package server.game;

import data.Player;
import data.PlayerData;

import java.util.Map;

public class PlayerManager {
    private static PlayerManager singleton = null;
    private static final int MAXPLAYERS = 10;

    private PlayerData data;

    public static PlayerManager getManager(){
        if(singleton == null) singleton = new PlayerManager();
        return singleton;
    }

    public PlayerManager(){
        data = new PlayerData(MAXPLAYERS);
    }

    public PlayerData getData(){
        return data;
    }

    public void addPlayer() throws IllegalStateException{
        int playerCount = data.getPlayerCount();
        if(playerCount == MAXPLAYERS) throw new IllegalStateException("Too many Players");

        data.getPlayers()[data.playerCount] = new Player(data.playerCount++);
    }

    public void changeName(int id, String newName){
        data.getPlayer(id).setName(newName);
    }

    static boolean checkName(String s){
        return s.length() < 10 && s.length() > 3;
    }



}
