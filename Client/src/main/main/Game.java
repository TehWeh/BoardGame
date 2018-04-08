package main.main;

import main.lobby.LobbyMenu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

    private static Game game;

    public static Game startNewGame(String title){
        game = new Game(title);
        return game;
    }


    public Game(String title){
        super(title);
        this.addState(new MainMenu(StartMenu));
        this.addState(new LobbyMenu(Lobby));
    }

    public static final int StartMenu = 0;
    public static final int Lobby = 1;
    public static final int WorldMap = 2;

    public static void enterLobby(){
        game.enterState(Lobby);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(StartMenu).init(gameContainer, this);
        this.getState(Lobby).init(gameContainer, this);
        //this.getState(WorldMap).init(gameContainer, this);
        enterState(StartMenu);
    }
}
