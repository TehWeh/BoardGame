package data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Player implements Externalizable {
    int id;
    String name;

    static final int MAXLEN = 10;
    static final int MINLEN = 3;

    public Player(int id){
        this.id = id;
    }

    public void setName(String name) throws IllegalArgumentException{
        if(valid(name)) name = name;
        int x;
    }

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


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeObject(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        name = (String) in.readObject();
    }
}
