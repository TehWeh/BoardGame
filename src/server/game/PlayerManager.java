package server.game;

import data.Player;
import data.PlayerData;
import main.main.Main;

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
        System.out.println("Server sends " + data.playerCount + " Players");
        return data;
    }

    public void addPlayer(int id, String s) throws IllegalStateException{
        int playerCount = data.getPlayerCount();
        if(playerCount == MAXPLAYERS) throw new IllegalStateException("Too many Players");
        if(data.getPlayers()[id] != null) throw new IllegalStateException("Id already taken wtf");

        data.getPlayers()[id] = new Player(data.playerCount);
        Main.getEventLogger().addEntry("Added new Player with id" + playerCount);

        data.playerCount++;
    }

    public void changeName(int id, String newName){
        data.getPlayer(id).setName(newName);
    }



    static boolean checkName(String s){
        return s.length() < 10 && s.length() > 3;
    }



}
