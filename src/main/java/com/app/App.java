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
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.*;
import javafx.scene.layout.*;

public class App extends Application {

    VBox root;
    Scene scene;
    VBox info;
    String city;

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

    /**
     * Loads file menu.
     */
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

    /**
     * Loads search field and search button.
     */
    public void searchField() {
        HBox hbox = new HBox();
        Label label = new Label("Enter name of city:");
        TextField query = new TextField();
        Button search = new Button("Search");
        HBox.setMargin(label, new Insets(14, 10, 10, 10));
        HBox.setMargin(query, new Insets(10, 0, 10, 0));
        HBox.setMargin(search, new Insets(10, 0, 10, 10));
        search.setOnAction(e -> {
            try {
                city = query.getText();
                city.toLowerCase();
                city = city.replaceAll("\\s", "+");
                getWeather(city);
            } catch (IOException e1) {
                System.err.println(e1);
            }
        });
        hbox.getChildren().addAll(label, query, search);
        root.getChildren().add(hbox);
    }

    /**
     * Runs the program.
     */
    public void run() {
        loadFileMenu();
        searchField();
    }

    /**
     * Gets the weather.
     * 
     * @param content name of city
     * @throws IOException
     */
    public void getWeather(String content) throws IOException {
        if (info != null) {
            info.getChildren().clear();
        }
        String sUrl = "http://api.weatherstack.com/current?access_key=1d73df4c4434a15e83327f4319fdab3e&query="
                + content;
        URL url = new URL(sUrl);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        JsonElement je = JsonParser.parseReader(reader);
        JsonObject jRoot = je.getAsJsonObject();
        printWeather(jRoot);
    }

    public void printWeather(JsonObject result) {
        info = new VBox(5);
        JsonObject jLocation = result.getAsJsonObject("location");
        JsonElement jName = jLocation.get("name");
        JsonElement jCountry = jLocation.get("country");
        JsonElement jTime = jLocation.get("localtime");
        JsonObject jCurrent = result.getAsJsonObject("current");
        JsonElement jTemperature = jCurrent.get("temperature");
        String tempInCelsius = jTemperature.getAsString();
        int celsius = Integer.parseInt(tempInCelsius);
        double tempInFahrenheit = (celsius * (9 / 5)) + 32;
        JsonElement jFeelsLike = jCurrent.get("feelslike");
        JsonElement jHumidity = jCurrent.get("humidity");
        JsonElement jWindSpeed = jCurrent.get("wind_speed");
        JsonElement jWindDegree = jCurrent.get("wind_degree");
        JsonArray weatherIcon = jCurrent.getAsJsonArray("weather_icons");
        String imageLink = weatherIcon.get(0).getAsString();
        JsonArray weatherDescription = jCurrent.getAsJsonArray("weather_descriptions");
        String description = weatherDescription.get(0).getAsString();

        Image image = new Image(imageLink);
        ImageView imgView = new ImageView(image);

        Label name = new Label("Name: " + jName.getAsString());
        Label country = new Label("Country: " + jCountry.getAsString());
        Label time = new Label("Time: " + jTime.getAsString());
        Label temperature = new Label("Temperature: " + jTemperature.getAsString() + "°");
        Label tempInF = new Label("Temperature: " + tempInFahrenheit + "°");
        Label tempDescription = new Label("Description: " + description);
        Label feelsLike = new Label("Feels Like: " + jFeelsLike.getAsString());
        Label humidity = new Label("Humidity: " + jHumidity.getAsString());
        Label windSpeed = new Label("Wind Speed: " + jWindSpeed.getAsString());
        Label windDegree = new Label("Wind Degree: " + jWindDegree.getAsString());

        info.getChildren().addAll(imgView, name, country, time, temperature, tempInF, tempDescription, feelsLike,
                humidity, windSpeed, windDegree);
        info.setAlignment(Pos.CENTER);
        root.getChildren().add(info);
    }
}
