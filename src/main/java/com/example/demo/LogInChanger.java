package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LogInChanger {
    public static Button backBTN;
    public static Label register;
    public static Button loginBTN;
    public static TextField email;
    public static PasswordField password;

    public static Stage stage2;

    public static JSONObject receivedCredentials;
    public static void setup(Scene scene, Stage stage) {
        backBTN = (Button) scene.lookup("#backBTN");
        loginBTN = (Button) scene.lookup("#loginBTN");
        register = (Label) scene.lookup("#register");
        email = (TextField) scene.lookup("#userEmail");
        password = (PasswordField) scene.lookup("#userPassword");
        goToLink("https://macroquiet.com/register");
        stage2 = stage;
        backBTN.setOnAction(event -> {
            try {
                switchToScene(stage, "hello-view");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        loginBTN.setOnAction(event -> {
            try {
                LogInUser();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static void switchToScene(Stage stage, String name) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name+".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), stage.getWidth()-16, stage.getHeight()-39);
        stage.setScene(scene);
        stage.show();
        SceneChanger.setup(scene, stage);
    }
    private static void goToLink(String link) {
        register.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private static void LogInUser() throws InterruptedException, IOException {
        String userEmail = email.getText();
        String userPassword = password.getText();

        //Spremanje u JSON
        JSONObject userCredentials = new JSONObject();
        userCredentials.put("email", userEmail);
        userCredentials.put("password", userPassword);

        LoginUserTask loginUserTask = new LoginUserTask("https://macroquiet.herokuapp.com/auth/unity", userCredentials);
        Thread loginUserThread = new Thread(loginUserTask);
        loginUserThread.start();
        loginUserThread.join();
        //Stores user credentials
        receivedCredentials = loginUserTask.getResult();
        switchToScene(stage2, "hello-view");
    }
}
