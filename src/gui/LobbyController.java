package gui;

import connection.ConnectionManager;
import data.*;
import game.ClientGameManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import msg.lobby.SetReadyRequest;
import msg.lobby.UnjoinRequest;

public class LobbyController implements Controller, DataObserver<PlayerData> {

    @FXML private TableView table;
    @FXML private Button joinButton;
    @FXML private Button leaveButton;
    @FXML private Button readyButton;
    @FXML private TableColumn nameCol;
    @FXML private TableColumn colCol;
    @FXML private TableColumn idCol;
    @FXML private TableColumn readyCol;
    ObservableList<Player> content;

    @FXML
    private void initialize() {
        ClientDataContainer.getContainer().registerObserver(this);
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
        disableButtons();
        ClientDataContainer.getContainer().requestData();
    }
    
    private void fillTable(){
        PlayerData data = ClientDataContainer.getContainer().getData();

        content.clear();
        for(Player p : data.getPlayers()) if(p != null) {
            content.add(p);
        }
        boolean ready = ClientGameManager.getManager().playerReady();
        Platform.runLater(() -> readyButton.setText(ready ? "!Bereit" : "Bereit"));
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
        ClientDataContainer.getContainer().requestData();
    }

    @FXML public void handleReadyButton(){
        boolean ready = ClientGameManager.getManager().playerReady();
        ConnectionManager.getManager().sendMessage(new SetReadyRequest(!ready));
        readyButton.setDisable(true);
        ClientDataContainer.getContainer().requestData();
    }

    @FXML public void handleLeaveButton(){
        ConnectionManager.getManager().sendMessage(new UnjoinRequest());

        disableButtons();
        ClientDataContainer.getContainer().requestData();
    }

    @FXML public void handleRefreshButton() {
        ClientDataContainer.getContainer().requestData();
    }

    @Override
    public void handleStageShutdown() {
        ClientDataContainer.getContainer().unregisterObserver(this);
    }

    @Override
    public void onUpdate() {
        Platform.runLater(() -> {
            enableButtons();
            fillTable();
        });
    }
}