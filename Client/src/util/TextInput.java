package util;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

public class TextInput extends MapObject {
    TextField tf;
    Rectangle rec;

    public TextInput(int x, int y, int width, int height, double scale, GameContainer gc) {
        super(x, y, scale);
        tf = new TextField(gc, new TrueTypeFont(new java.awt.Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 12), false), x, y, width, height);
        rec = new Rectangle(x,y,width, height);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sg, Graphics graphics) {
        tf.render(gc, graphics);
        graphics.setColor(Color.cyan);
        graphics.draw(rec);
        graphics.setColor(Color.white);
    }

    public String getText(){
        return tf.getText();
    }
}
