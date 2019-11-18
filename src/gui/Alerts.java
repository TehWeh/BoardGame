package gui;

import javafx.scene.control.Alert;

public class Alerts {

    public static void alertError(String s, String title){
        Alert alert = new Alert(Alert.AlertType.ERROR, s);
        alert.setHeaderText(null);
        alert.setTitle(title    );
        alert.showAndWait();
        alert.setHeaderText("create DATABASE");
    }

    public static void alertError(String s){
        alertError(s, null);
    }
}
