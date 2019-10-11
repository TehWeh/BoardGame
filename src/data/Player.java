package data;

import main.main.Main;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Player extends DataObject {
    Integer id;
    String name;
    String color;
    boolean ready;

    static final int MAXLEN = 10;
    static final int MINLEN = 3;

    public Player(int id){
        this.id = id;
    }

    public Player(){}

    public Player(int id, String s){
        this(id);
        name = s;
        ready = false;
    }

    public void setReady(boolean b){
        ready = b;
    }

    public boolean getReady(){
        return ready;
    }

    public void setName(String name) throws IllegalArgumentException{
        if(valid(name)) name = name;
        int x;
    }

    public int getID(){
        return id;
    }

    public String getIdString(){
        return Integer.toString(id);
    }
    public String getReadyString() {return ready ? "Ja" : "Nein";}
    @Override
    public boolean equals(Object o){
        if(o.getClass() != this.getClass()) return false;
        return ((Player) o).id == this.id;
    }

    private boolean valid(String s) throws IllegalArgumentException{
        if(s.length() < MINLEN) throw new IllegalArgumentException("Zu kurzer Name");
        if(s.length() > MAXLEN) throw new IllegalArgumentException("Zu langer Name");
        return true;
    }

    public String getName(){
        return name;
    }


    //@Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeObject(name);
        out.writeBoolean(ready);
        //Main.getEventLogger().addEntry("Write: "+ name);

    }

    //@Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        name = (String) in.readObject();
        ready = in.readBoolean();
        //Main.getEventLogger().addEntry("Externalization: "+ name);

    }

}
