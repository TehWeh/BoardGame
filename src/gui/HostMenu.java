package gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.stage.WindowEvent;
import server.main.Server;

import static java.lang.Thread.sleep;

public class HostMenu extends Stage {
    Label serverStatus;
    Button startServer;
    Scene noServerScene;
    Label playerCount;
    Label phase;
    Label errorCount;
    Button kickall;
    Button progress;
    HBox buttonBox;

    private boolean active;

    static final int UPDATE_INTERVAL = 1000;

    public HostMenu(){
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

        phase = new Label();

        kickall = new Button("Kick all");
        kickall.setOnMouseClicked(mouseEvent ->{
            Server.getSingleton().kickAll();
        });
        progress = new Button("Starten"); // TEMP

        buttonBox = new HBox();
        buttonBox.getChildren().addAll(kickall, progress);

        haupt.getChildren().addAll(hb, startServer, playerCount, errorCount, phase, buttonBox);
        noServerScene = new Scene(haupt, 400, 300);


        setScene(noServerScene);
        setResizable(false);
        show();

        setTitle("Serverübersicht");

        active = true;
        new Thread(() -> {
            while(active){
                updateGUI();
                try {
                    sleep(UPDATE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        setOnCloseRequest(e -> active = false);
    }


    private void updateGUI(){
        Platform.runLater(() -> {
            if(Server.isActive()){
                serverStatus.setText("Server is running");
                startServer.setDisable(true);

                playerCount.setVisible(true);
                playerCount.setText("Number of active players: " + Server.getNumberOfPlayers());

                errorCount.setVisible(true);
                errorCount.setText("Number of errors: " + Server.getNumberOfErrors());
    
                phase.setVisible(true);
                phase.setText("Serverstatus: " + Server.getStatus());

                buttonBox.setVisible(true);
            }
            else{
                serverStatus.setText("Not running server detected");
                startServer.setDisable(false);
                playerCount.setVisible(false);
                errorCount.setVisible(false);
                phase.setVisible(false);
                buttonBox.setVisible(false);
            }
        });
    }
}
