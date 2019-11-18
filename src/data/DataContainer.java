package data;

import connection.ConnectionManager;
import msg.ClientMessage;

import java.util.ArrayList;
import java.util.List;


public class DataContainer<D extends DataObject> {
    private volatile D data;
    private ClientMessage requestMsg;

    private static final int REQUEST_INTERVALL = 60_000;
    private List<DataObserver<D>> obervers;

    public DataContainer(ClientMessage request){
        data = null;
        obervers = new ArrayList<>();
        requestMsg = request;

    }

    public synchronized void registerObserver(DataObserver<D> o){
        obervers.add(o);
    }

    public synchronized void unregisterObserver(DataObserver<D> o){obervers.remove(o);}

    public synchronized D getData() {
        if(data == null){
             requestData();
             while(data == null) {
                 try {
                     Thread.sleep(100);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
        }
        return data;
    }

    public synchronized void receive(D d){
        System.out.println("receive");
        data = d;
        new Thread(() -> {
                for(DataObserver o : obervers) o.onUpdate();
        }).start();
    }

    public synchronized void requestData(){
        ConnectionManager.getManager().sendMessage(requestMsg);
    }



}
