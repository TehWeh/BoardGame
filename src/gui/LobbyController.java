package gui;

import connection.ConnectionManager;
import data.ClientDataContainer;
import data.DataItem;
import data.Player;
import data.PlayerData;
import game.ClientGameManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.main.Main;
import msg.lobby.SetReadyRequest;
import msg.lobby.UnjoinRequest;

import java.util.concurrent.ExecutionException;

public class LobbyController implements Controller{

    //Vbox - Hauptlayout

    static final int UPDATE_INTERVAL = 100;

    @FXML private TableView table;
    @FXML private Button joinButton;
    @FXML private Button leaveButton;
    @FXML private Button readyButton;
    @FXML private TableColumn nameCol;
    @FXML private TableColumn colCol;
    @FXML private TableColumn idCol;
    @FXML private TableColumn readyCol;
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
        readyCol.setCellValueFactory(
                new PropertyValueFactory<Player, Boolean>("readyString")
        );
        content = FXCollections.observableArrayList();
        table.setItems(content);
        fillTable();
        enableButtons();

        new Thread(() -> {
            running = true;
            while(running){
                fillTable();
                try {
                    Thread.sleep(UPDATE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
         }).start();
    }
    
    private void fillTable(){
        PlayerData data = ClientDataContainer.getContainer().getPlayerdata();
        content.clear();
        for(Player p : data.getPlayers()) if(p != null) {
            content.add(p);
            //Main.getEventLogger().addEntry(p.getName() + " " + p.getID());
        }
        boolean ready = ClientGameManager.getManager().playerReady();
        Platform.runLater(() -> {readyButton.setText(ready ? "!Bereit" : "Bereit");});
    }

    private void enableButtons(){
        boolean registered = ClientGameManager.getManager().registered();
        readyButton.setDisable(!registered);
        leaveButton.setDisable(!registered);
        joinButton.setDisable(registered);
    }

    private void disableButtons(){
        readyButton.setDisable(true);
        leaveButton.setDisable(true);
        joinButton.setDisable(true);
    }

    @FXML public void handleJoinButtonSubmit(){
        ClientGameManager.getManager().register();
        disableButtons();
        ClientDataContainer.getContainer().getDataItem().requestData(new Thread(() -> {
            enableButtons();
        }));
    }

    @FXML public void handleReadyButton(){
        boolean ready = ClientGameManager.getManager().playerReady();
        ConnectionManager.getManager().sendMessage(new SetReadyRequest(!ready));
        readyButton.setDisable(true);
        ClientDataContainer.getContainer().getDataItem().requestData(new Thread(() -> {
            enableButtons();
        }));
    }

    @FXML public void handleLeaveButton(){
        ConnectionManager.getManager().sendMessage(new UnjoinRequest());

        disableButtons();
        ClientDataContainer.getContainer().getDataItem().requestData(new Thread(() -> {
            enableButtons();
        }));
    }

    @FXML public void handleRefreshButton() {
        DataItem item = ClientDataContainer.getContainer().getDataItem();
        item.requestData(new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> enableButtons());
                return null;
            }
        }
        );
    }

    @Override
    public void handleStageShutdown() {
        running = false;
    }
}