package util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface Renderable {
    void render(GameContainer gc, StateBasedGame sg, Graphics graphics);
}
