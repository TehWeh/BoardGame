package util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;



public class Popup implements Renderable {

    int x, y;
    int width, height;



    @Override
    public void render(GameContainer gc, StateBasedGame sg, Graphics graphics) {
        graphics.draw(new Rectangle(40, 40, 400, 400));
        graphics.drawString("Hallo", 50,50);

    }
}
