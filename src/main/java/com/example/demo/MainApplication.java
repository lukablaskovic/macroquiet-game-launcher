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
    public static JSONObject receivedCredentials;
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

        String userEmail = "lukablaskovic2000@gmail.com";
        String userPassword = "123";

        //Spremanje u JSON
        JSONObject userCredentials = new JSONObject();
        userCredentials.put("email", userEmail);
        userCredentials.put("password", userPassword);
        System.out.println(userCredentials);


        LoginUserTask loginUserTask = new LoginUserTask("https://macroquiet.herokuapp.com/auth/unity", userCredentials);
        Thread loginUserThread = new Thread(loginUserTask);
        loginUserThread.start();
        loginUserThread.join();
        //Stores user credentials
        receivedCredentials = loginUserTask.getResult();

    }
    public static void main(String[] args) {
        launch();
    }

}

