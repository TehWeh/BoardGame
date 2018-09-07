package main.main;

        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
        primaryStage.setTitle("Boardgame");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}