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
    public static ArrayList<String> carouselURLs;
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 840);
        stage.setTitle("MacroQuiet Launcher");
        stage.setScene(scene);
        stage.show();

        Task task = new Task("https://macroquiet.herokuapp.com/admin/data/carouselPictures");
        Thread taskThread = new Thread(task);
        taskThread.start();

        taskThread.join();

        //URLs are saved as Array of strings in carouselURLs
        carouselURLs = task.getResult();
        System.out.println(carouselURLs);
        SceneChanger.setup(scene, stage);
    }
    public static void main(String[] args) {
        launch();
    }
    class Task implements Runnable {
        private String endpoint;
        public Task(String endpoint){
            this.endpoint = endpoint;
        }
        ArrayList<String> result = new ArrayList<>();
        //Fetch carousel images thread

        public void run() {
            try {
                APIConnector connector = new APIConnector();
                JSONArray data = connector.get(endpoint);

                for (int i = 0; i < data.length(); i++) {
                    JSONObject object = data.getJSONObject(i);
                    result.add(object.getString("url"));
                }
            }catch(Exception e) {
                System.out.println("Error" + e.getMessage());
            }
        }
        public ArrayList<String> getResult(){return result;}
    }


}

