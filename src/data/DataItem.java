package data;

import connection.ConnectionManager;
import javafx.concurrent.Task;
import msg.ClientMessage;
import msg.ClientMessageTask;

import java.util.Timer;
import java.util.concurrent.*;

public class DataItem<T extends DataObject>{
    private T content;
    private int inval;
    private ClientMessage msg;

    private BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<>(10);

    public DataItem(int inval, ClientMessage m){
        msg = m;
        this.inval = inval;
        new Timer().schedule(new ClientMessageTask(msg), 50);
        content = null;
    }

    private void executeTasks() throws InterruptedException {
        synchronized (tasks){
            while(!tasks.isEmpty()) tasks.take().run();
        }
    }

    public synchronized void receive(T content){
        this.content = content;
        new Thread(() -> {
            try {
                executeTasks();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Timer().schedule(new ClientMessageTask(msg), inval);
    }

    public synchronized void requestData(Runnable r) {
        tasks.add(r);
        requestData();
    }

    public synchronized void requestData(){
        ConnectionManager.getManager().sendMessage(msg);
    }

    public synchronized T getData(){
        try {
            if(content == null) wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return content;
    }
}