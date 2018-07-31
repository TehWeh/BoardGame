package gui;

import connection.ConnectionManager;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class MainMenu extends Stage {
    public MainMenu() {

        //Vbox - Hauptlayout
        VBox haupt = new VBox();
        haupt.setSpacing(10);
        haupt.setPadding(new Insets(8, 8, 8, 8));

        //HBox top, obere Leiste mit Buttons
        //HBox top = new HBox();
        //top.setSpacing(10);

        //Buttons in top
        Button host = new Button();
        host.setText("Hosteinstellungen");

        Button join = new Button("Join Game");

        Button settings = new Button("Settings");

        host.setOnMouseClicked(mouseEvent -> new HostMenu().show());


        join.setOnMouseClicked(mouseEvent -> {
            try {
                ConnectionManager.getManager().connect();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No Server running on localhost");
                //alert.setTitle("Error");
                alert.showAndWait();
            } catch(IllegalStateException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                //alert.setTitle("Error");
                alert.showAndWait();
            }
        });


        //Alle Komponenten "zusammenfügen"
        haupt.getChildren().addAll(host, join, settings);
        Scene scene = new Scene(haupt, 400, 300);

        setScene(scene);
        setResizable(false);
        show();
    }
}
