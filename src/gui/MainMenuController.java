package gui;

import connection.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;


public class MainMenuController implements Controller{

    @FXML protected void handleJoinButtonAction() {
        try {
            ConnectionManager.getManager().connect();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No running server found on localhost");
            alert.showAndWait();
            e.printStackTrace();
        } catch(IllegalStateException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML protected void handleHostButtonAction(){
        new HostMenu().show();
    }

    @FXML protected  void handleClientButtonAction(){
        WindowManager.getManager().newWindow("ClientSettings.fxml", "Settings");
    }

    @Override
    public void handleStageShutdown() {

    }
}