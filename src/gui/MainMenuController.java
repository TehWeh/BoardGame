package gui;

import connection.ConnectionManager;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenuController implements Controller{

    @FXML protected void handleJoinButtonAction() {
        if(!ConnectionManager.getManager().isConntected())
            try {
                ConnectionManager.getManager().connect();
            } catch (IOException e) {
                e.printStackTrace();
                Alerts.alertError("No running server found on localhost");
            } catch(IllegalStateException e){
                e.printStackTrace();
                Alerts.alertError(e.getMessage());
            }
        WindowManager.getManager().newWindow("Lobby.fxml", "Lobby");
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