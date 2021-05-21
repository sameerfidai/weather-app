package com.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.*;
import javafx.scene.layout.*;

public class App extends Application {

    VBox root;
    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        // setup scene
        root = new VBox();
        scene = new Scene(root);

        run();

        // setup stage
        stage.setTitle("WeatherApp");
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(600);
        stage.sizeToScene();
        stage.show();
    }

    public void loadFileMenu() {
        HBox hbox = new HBox();
        MenuBar menuBar = new MenuBar();
        menuBar.setMinWidth(600);
        Menu file = new Menu("File");
        menuBar.getMenus().add(file);
        MenuItem exit = new MenuItem("Exit");
        file.getItems().add(exit);
        exit.setOnAction(event -> System.exit(0));
        hbox.getChildren().add(menuBar);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        root.getChildren().add(hbox);
    }

    public void run() {
        loadFileMenu();
    }
}
