package main.util;

import org.newdawn.slick.Graphics;

public abstract class Renderable {
    int offsX = 0, offsY = 0;
    double scale;

    public Renderable(int x, int y, double scale){
        offsX = x;
        offsY = y;
    }

    public abstract void render(Graphics graphics);

}
