package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApplication extends Application {
    public static ArrayList<String> carouselURLs;
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 840);
        stage.setTitle("MacroQuiet Launcher");
        stage.setScene(scene);
        stage.show();

        LoadImagesTask loadImagesTask = new LoadImagesTask("https://macroquiet.herokuapp.com/admin/data/carouselPictures");
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

