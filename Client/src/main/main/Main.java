package main.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public static final String title = "Warrior Knights";

    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;

    public static void main(String[] args){
        AppGameContainer agc;
        try{
            agc = new AppGameContainer(Game.startNewGame(title));
            agc.setFullscreen(true);

            //agc.setDisplayMode(1024, 768, true);
            agc.setDisplayMode(WIDTH, HEIGHT, true);
            agc.start();
        }
        catch (SlickException e){
            e.printStackTrace();
        }
    }
}
