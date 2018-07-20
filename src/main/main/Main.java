package main.main;

import Log.FileLogger;
import Log.Logger;
import gui.MainMenu;

public class Main {
    static private Logger eventLogger;

    public static void main(String[] args){
        eventLogger = new FileLogger("log/eventlog.txt", "EventLogger");
        eventLogger.start();

        Application.launch(Application.class);
    }

    public Logger getEventLogger(){
        return eventLogger;
    }
}
