package gui;

import connection.ConnectionManager;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenuController implements Controller{

    @FXML protected void handleJoinButtonAction() {
        if(!ConnectionManager.getManager().isConntected())
            try {
                ConnectionManager.getManager().connect();
                WindowManager.getManager().newWindow("Lobby.fxml", "Lobby");
            } catch (IOException e) {
                Alerts.alertError("No running server found on localhost");
                //e.printStackTrace();
            } catch(IllegalStateException e){
                Alerts.alertError(e.getMessage());
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