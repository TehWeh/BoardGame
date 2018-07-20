package Log;

public abstract class Logger {
    public abstract void addEntry(String s);

    public abstract void start();
    public abstract void close();
}
