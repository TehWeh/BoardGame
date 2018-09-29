package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            if(root == null){
                URL url = new File("fxml/" + resource).toURL();
                root = FXMLLoader.load(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
        stage.setTitle(title);

        stage.show();
    }
}
