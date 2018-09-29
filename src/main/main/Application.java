package main.main;

import gui.WindowManager;

import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        WindowManager.getManager().newWindow("MainMenu.fxml", "Boardgame", primaryStage);

    }
}
