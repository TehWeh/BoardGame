package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class MainMenu extends Stage {
    public MainMenu() {

        //Vbox - Hauptlayout
        VBox haupt = new VBox();
        haupt.setSpacing(10);
        haupt.setPadding(new Insets(8, 8, 8, 8));

        //HBox top, obere Leiste mit Buttons
        HBox top = new HBox();
        top.setSpacing(10);

        //Buttons in top
        Button buttonSave = new Button();
        buttonSave.setPrefSize(20, 20);
        ImageView save = new ImageView( new Image(getClass().getResourceAsStream("/save.png")));
        save.setPreserveRatio(true);
        save.setFitHeight(30);
        buttonSave.setGraphic(save);

        Button buttonShow = new Button("Show");
        buttonShow.setPrefSize(100, 20);

        //buttom enhält alle Elemente unter top
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(0, 0, 0, 0));
        bottom.setSpacing(10);


        //Pane kann als Zeichenfläche genutzt werden
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: white;");
        canvas.setPrefSize(700, 550);

        //TabPane mit Tabs einfügen
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab tab1 = new Tab();
        tab1.setText("Content");
        Tab tab2 = new Tab();
        tab2.setText("Style");
        tabPane.getTabs().addAll(tab1, tab2);
        tabPane.setPrefHeight(600);
        tabPane.setPrefWidth(220);

        Pane contentTab1 = new Pane();
        contentTab1.setStyle("-fx-background-color: #eeeeee;");
        tab1.setContent(contentTab1);

        Pane contentTab2 = new Pane();
        contentTab2.setStyle("-fx-background-color: #eeeeee;");
        tab2.setContent(contentTab2);

        //Buttons und Textfeld unter Tabpane
        Button insertButton = new Button();
        insertButton.setText("Add word");
        insertButton.setPrefWidth(220);

        TextField wordText = new TextField();

        //Neue Vbox mit Tabs, Textfeld und Button
        //Insert VBOX
        VBox bottomRight = new VBox();
        bottomRight.setPadding(new Insets(0));
        bottomRight.setSpacing(8);
        bottomRight.setPrefHeight(800);
        bottomRight.setAlignment(Pos.CENTER);


        //Alle Komponenten "zusammenfügen"
        bottomRight.getChildren().addAll(tabPane, wordText, insertButton);
        bottom.getChildren().addAll(canvas, bottomRight);
        haupt.getChildren().addAll(top, bottom);
        Scene scene = new Scene(haupt, 950, 700);
        top.getChildren().addAll(buttonSave, buttonShow);


        AtomicInteger i = new AtomicInteger(); //Muss benutzt werden, da normaler Integer in Lamba-Expression nicht benutzt werden kann

        //Buttonlogik
        insertButton.setOnAction((e) -> {
            if (!wordText.getText().isEmpty()) {
                Text text = new Text(wordText.getText());
                text.setX(100);
                text.setY(200);
                canvas.getChildren().add(text);

                Text t2 = new Text();
                t2.setText(wordText.getText());
                t2.setX(10);
                t2.setY(25 + 20 * i.getAndIncrement());
                contentTab1.getChildren().add(t2);
            }
        });

        setScene(scene);
        setResizable(false);
        show();
    }
}
