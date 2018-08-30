package server.game;

import data.Player;

import java.util.Map;

public class PlayerManager {
    private static PlayerManager singleton = null;

    private Map<Integer, Player> playerMap;

    public static PlayerManager getManager(){
        if(singleton == null) singleton = new PlayerManager();
        return singleton;
    }


    public void addPlayer(int id){
        if(playerMap.containsKey(id)) throw new IllegalArgumentException("ID " + id + " already taken");
        playerMap.put(id, new Player(id));
    }

    public void changeName(int id, String newName){

    }

    static boolean checkName(String s){
        return s.length() < 10 && s.length() > 3;
    }



}
