package gui;

import javafx.scene.control.Alert;

public class Alerts {

    public static void alertError(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR,
                s);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
