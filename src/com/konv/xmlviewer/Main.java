package com.konv.xmlviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("XML Viewer");
        primaryStage.setScene(new Scene(root, 600, 428));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("xml-icon.png")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
