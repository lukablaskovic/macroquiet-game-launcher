package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LogInChanger {
    public static Button loginBTN;
    public static Label register;
    public static void setup(Scene scene, Stage stage) {
        loginBTN = (Button) scene.lookup("#loginBTN");
        register = (Label) scene.lookup("#register");
        goToLink("https://macroquiet.com/register");

        loginBTN.setOnAction(event -> {
            try {
                switchToScene(stage, "hello-view");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static void switchToScene(Stage stage, String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
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
}
