package main.main;

import main.util.Renderable;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import server.ConnectionListener;

public class MainMenu extends BasicGameState {
    private boolean toggle = false;

    private class MenuItem extends Renderable{
        Rectangle rec;
        String text;
        boolean mouseIn;
        boolean active;
        public MenuItem(Rectangle rec, String text){
            super((int) rec.getX(), (int) rec.getY(), 1.0);
            this.rec  = rec;
            this.text = text;
            this.active = false;

        }
        public void action(){

        }

        @Override
        public void render(Graphics graphics) {


            if(mouseIn) graphics.setLineWidth(3.0f);
            graphics.draw(rec);

            graphics.setLineWidth(1.0f);

            graphics.drawString(text, rec.getX() + 10, rec.getY() + 10);
        }
    }


    private MenuItem[] items;

    private String serverStatus;



    @Override
    public int getID() {
        return Game.StartMenu;
    }

    public MainMenu(int id){
        items    = new MenuItem[4];
        items[0] = new MenuItem( new Rectangle(190, 190, 200, 50), "Einstellungen");
        items[1] = new MenuItem(new Rectangle(190, 390, 200, 50), "Spiel hosten"){
            public void action(){
                ConnectionListener.startListening();
            }
        };
        items[2] = new MenuItem(new Rectangle(190, 590, 200, 50), "Spiel beitreten");
        items[3] = new MenuItem(new Rectangle(190, 790, 200, 50), "Programm schließen");

        serverStatus = "";
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        for(MenuItem mi : items) mi.render(graphics);
        graphics.drawString(serverStatus, 1500, 50);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i){
        int x = Mouse.getX(), y = Math.abs(Mouse.getY()-Main.HEIGHT-1);
        for(MenuItem mi : items) mi.mouseIn = mi.rec.contains(x,y);
        serverStatus = ConnectionListener.getStatus();

    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
        System.out.println("CLicked");
        for(MenuItem mi : items) if(mi.rec.contains(x, y)) mi.action();
    }
}
