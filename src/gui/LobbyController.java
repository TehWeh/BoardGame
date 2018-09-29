package gui;


import config.ConfigurationManager;
import connection.ConnectionManager;
import data.ClientDataContainer;
import data.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.main.Main;
import msg.PlayerDataRequest;
import msg.meta.PlayerRegisterRequest;


public class LobbyController{

    //Vbox - Hauptlayout

    static final int UPDATE_INTERVAL = 1000;

    @FXML private TableView table;
    @FXML private Button join;

    @FXML
    private void initialize(){
        PlayerData p = ClientDataContainer.getContainer().getPlayerdata();
        Main.getEventLogger().addEntry("received playercount: " + p.getPlayerCount());
        //  

    @FXML public void handleJoinButtonSubmit(){
        ConnectionManager.getManager().sendMessage(new PlayerRegisterRequest(ConfigurationManager.getManager().getPlayerName()));
    }

}
