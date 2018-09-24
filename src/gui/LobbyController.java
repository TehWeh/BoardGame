package gui;


import data.ClientDataContainer;
import data.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.main.Main;


public class LobbyController{

    //Vbox - Hauptlayout

    static final int UPDATE_INTERVAL = 1000;

    @FXML private TableView table;

    @FXML
    private void initialize(){
        PlayerData p = ClientDataContainer.getContainer().getPlayerdata();
        Main.getEventLogger().addEntry("received playercount: " + p.getPlayerCount());
    }

}
