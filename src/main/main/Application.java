package main.main;

import gui.MainMenu;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = new MainMenu();
        window.setTitle("Boardgame");
    }
}