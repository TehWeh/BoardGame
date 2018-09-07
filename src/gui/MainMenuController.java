package gui;

import connection.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class MainMenuController extends Stage {

    @FXML protected void handleJoinButtonAction() {
        try {
            ConnectionManager.getManager().connect();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Server running on localhost");
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
}