package Log;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FileLogger extends Logger implements LogSource{
    private PrintWriter writer;
    private BlockingQueue<String> q;
    private boolean running;

    private String name;

    public FileLogger(String name) {
        this.name = name;
        q = new ArrayBlockingQueue<String>(20);
    }
    public FileLogger(String file, String name) {
        this(name);
        setOutputFile(file);
    }

    void setOutputFile(String fp){
        File f = new File(fp);
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer = new PrintWriter(fp, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        q = new ArrayBlockingQueue(20);
        start();
        new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        String s = q.take();
                        System.out.println(s);

                        writer.println(s);
                        writer.flush();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                writer.flush();
                writer.close();
            }
        }).start();
        log("New File Logger started");
    }

    public void addEntry(String s){
        q.add(s);
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void close() {
        running = false;
    }

    @Override
    public void log(String s) {
        addEntry(name + ": " + s);
    }
}
