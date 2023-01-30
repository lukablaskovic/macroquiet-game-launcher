package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class MainApplication extends Application {
    public static ArrayList<String> carouselURLs = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 840);


        //Image image = new Image("images/appicon.png");
        //stage.getIcons().add(image);

        stage.setTitle("MacroQuiet Launcher");
        stage.setScene(scene);
        stage.show();


        GetTask loadImagesTask = new GetTask("https://macroquiet.herokuapp.com/admin/data/carouselPictures", JSONArray.class);
        //Start LoadImages task
        Thread loadImagesThread = new Thread(loadImagesTask);
        loadImagesThread.start();
        loadImagesThread.join();

        carouselURLs = loadImagesTask.getResult();
        //Change images in scene

        SceneChanger.setup(scene, stage);
    }
    public static void main(String[] args) {
        launch();
    }

}

