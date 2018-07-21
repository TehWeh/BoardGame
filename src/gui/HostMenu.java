package gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import server.main.Server;


public class HostMenu extends Stage {

    //Vbox - Hauptlayout

    Label serverStatus;

    public HostMenu(){
        initGUI();
        fillGUI();
    }

    private void initGUI(){
        VBox haupt = new VBox();
        haupt.setSpacing(10);
        haupt.setPadding(new Insets(8, 8, 8, 8));

        Label serverStatus = new Label();

        HBox hb = new HBox();
        hb.getChildren().addAll(serverStatus);

        haupt.getChildren().addAll(hb);
        Scene scene = new Scene(haupt, 400, 300);

        setScene(scene);
        setResizable(false);
        show();
    }

    private void fillGUI(){
        if(Server.isActive()) serverStatus.setText("Server is running");
        else serverStatus.setText("Not running server detected");
    }
}
