package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainApplication extends Application {
    public static ArrayList<String> carouselURLs = new ArrayList<>();
    public static ArrayList<String> userData;
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 840);
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

        String username = "blaskec";
        String URL = String.format("https://macroquiet.herokuapp.com/unity/user/profile?username=%s",username);
        GetTask loadUserDataTask = new GetTask(URL, JSONObject.class);
        Thread loadUserDataThread = new Thread(loadUserDataTask);
        loadUserDataThread.start();
        loadUserDataThread.join();
        userData = loadUserDataTask.getResult();
        System.out.println("userData: results:" + userData);


    }
    public static void main(String[] args) {
        launch();
    }

}

