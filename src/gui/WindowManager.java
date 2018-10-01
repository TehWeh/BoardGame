package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.main.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class WindowManager {

    private static WindowManager singleton;



    public static WindowManager getManager(){
        if(singleton == null) singleton = new WindowManager();
        return singleton;
    }

    public WindowManager(){
    }

    public void newWindow(String resource, String title){
        newWindow(resource, title, new Stage());
    }

    public void newWindow(String resource, String title, Stage stage){
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/" + resource);
            File file = new File(url.getFile());

            FileInputStream fxmlStream = new FileInputStream(file);
            root = fxmlLoader.load(fxmlStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
        Controller controller = fxmlLoader.getController();
        Main.getEventLogger().addEntry("New Controller: " + controller.toString());
        stage.setOnCloseRequest(e -> controller.handleStageShutdown());
    }
}
