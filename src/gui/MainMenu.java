package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


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

        host.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new HostMenu().show();
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
