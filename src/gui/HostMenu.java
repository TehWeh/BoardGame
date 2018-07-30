package gui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import server.main.Server;

import static java.lang.Thread.sleep;


public class HostMenu extends Stage {

    //Vbox - Hauptlayout

    Label serverStatus;
    Button startServer;
    Scene noServerScene;
    Label playerCount;

    Label errorCount;

    static final int UPDATE_INTERVAL = 1000;

    public HostMenu(){
        initGUI();
        //fillGUI();
    }

    private void initGUI(){
        VBox haupt = new VBox();
        haupt.setSpacing(10);
        haupt.setPadding(new Insets(8, 8, 8, 8));

        serverStatus = new Label();

        HBox hb = new HBox();
        hb.getChildren().addAll(serverStatus);

        startServer = new Button("Server starten");

        startServer.setOnMouseClicked(mouseEvent -> {
            Server.startServer();
        });

        playerCount = new Label();
        errorCount = new Label();

        haupt.getChildren().addAll(hb, startServer, playerCount, errorCount);
        noServerScene = new Scene(haupt, 400, 300);



        setScene(noServerScene);
        setResizable(false);
        show();

        setTitle("Serverübersicht");

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
        Platform.runLater(() -> {
            if(Server.isActive()){
                serverStatus.setText("Server is running");
                startServer.setDisable(true);

                playerCount.setVisible(true);
                playerCount.setText("Number of active players: " + Server.getNumberOfPlayers());

                errorCount.setVisible(true);
                errorCount.setText("Number of fatal errors: " + Server.getNumberOfErrors());
            }
            else{
                serverStatus.setText("Not running server detected");
                startServer.setDisable(false);
                playerCount.setVisible(false);
                errorCount.setVisible(false);

            }

        });

    }
}
