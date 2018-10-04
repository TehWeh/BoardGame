package gui;


import config.ConfigurationManager;
import connection.ConnectionManager;
import data.ClientDataContainer;
import data.Player;
import data.PlayerData;
import game.ClientGameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.main.Main;
import msg.meta.PlayerRegisterRequest;

import java.util.ArrayList;


public class LobbyController implements Controller{

    //Vbox - Hauptlayout

    static final int UPDATE_INTERVAL = 1000;

    @FXML private TableView table;
    @FXML private Button joinButton;
    @FXML private Button leaveButton;
    @FXML private Button readyButton;
    @FXML private TableColumn nameCol;
    @FXML private TableColumn colCol;
    @FXML private TableColumn idCol;
    ObservableList<Player> content;

    private boolean running;

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Player,String>("name")
        );
        idCol.setCellValueFactory(
                new PropertyValueFactory<Player,String>("idString")
        );
        content = FXCollections.observableArrayList();
        table.setItems(content);
        fillTable();

        new Thread(() -> {
            running = true;
            while(running){
                fillTable();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
         }).start();
    }
    
    private void fillTable(){
        Main.getEventLogger().addEntry("Filling table");
        PlayerData data = ClientDataContainer.getContainer().getPlayerdata();
        content.clear();
        for(Player p : data.getPlayers()) if(p != null) {
            content.add(p);
            Main.getEventLogger().addEntry(p.getName() + " " + p.getID());
        }
        boolean registered = ClientGameManager.getManager().registered();
        Main.getEventLogger().addEntry("Registered = " + registered);
        readyButton.setDisable(!registered);
        leaveButton.setDisable(!registered);
        joinButton.setDisable(registered);
    }

    @FXML public void handleJoinButtonSubmit(){
        ConnectionManager.getManager().sendMessage(new PlayerRegisterRequest(ConfigurationManager.getManager().getPlayerName()));
    }

    @Override
    public void handleStageShutdown() {
        running = false;
    }
}
