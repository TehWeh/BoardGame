package data;

public class Player {
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
}
