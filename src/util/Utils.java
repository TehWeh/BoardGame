package util;

import main.main.Main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Utils {

    public static void getSize(Object o) {
        try {
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            objectOutputStream.close();

            Main.getEventLogger().addEntry(o.toString() + " - size: " + byteOutputStream.toByteArray().length);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
