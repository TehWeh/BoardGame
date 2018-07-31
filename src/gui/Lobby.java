package gui;

import javafx.geometry.Insets;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;

public class Lobby extends Stage {

        //Vbox - Hauptlayout

    static final int UPDATE_INTERVAL = 1000;
    public Lobby(){
        VBox haupt = new VBox();
        haupt.setSpacing(10);
        haupt.setPadding(new Insets(8, 8, 8, 8));


            new Thread(() -> {
                while(true){
                    updateGUI();
                    try {
                        sleep(UPDATE_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
    }




    private void updateGUI(){


    }

}
