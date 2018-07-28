package util;

public abstract class MapObject implements Renderable{
    int offsX = 0, offsY = 0;
    double scale;

    public MapObject(int x, int y, double scale) {
        offsX = x;
        offsY = y;
    }

    //public abstract void render(GameContainer gc, StateBasedGame sg, Graphics graphics);
}
