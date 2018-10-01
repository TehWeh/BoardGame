package gui;

import config.ConfigurationManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.main.Main;

import java.io.IOException;

public class ClientSettingsController implements Controller{

    @FXML
    private TextField playerName;

    @FXML
    private TextField serverIP;

    @FXML
    public void initialize(){
        Main.getEventLogger().addEntry("Loading config screen");
        try {
            ConfigurationManager.getManager().readFromConfigFile();
        } catch (ClassNotFoundException e) {
            Alerts.alertError("Unknown Configuration Content");
            e.printStackTrace();
        } catch (IOException e) {
            Alerts.alertError("Unable to read File");
            e.printStackTrace();
        }
        playerName.setText(ConfigurationManager.getManager().getPlayerName());
        serverIP.setText(ConfigurationManager.getManager().getServerIP());

    }

    @FXML
    public void updatePlayerName(){
        ConfigurationManager.getManager().setPlayerName(playerName.getText());
    }

    @FXML
    public void updateServerIP(){
        ConfigurationManager.getManager().setServerIP(serverIP.getText());
    }

    @FXML
    public void handleSaveButton(){
        try {
            Main.getEventLogger().addEntry("Save Button Pressed");
            ConfigurationManager.getManager().writeConfigFile();
        } catch (IOException e) {
           Alerts.alertError("Error while saving configurations");
        }
    }


    @Override
    public void handleStageShutdown() {

    }
}
