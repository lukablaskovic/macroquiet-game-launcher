package com.example.demo;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.awt.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SceneChanger {
    public static HBox gameCarousel;
    public static Button watchTrailer;
    public static HBox enlargeImage;
    public static HBox StrandedAwayBTN;
    public static HBox DogeBTN;
    public static Pane gameTitle;
    public static StackPane gameCover;
    public static void setup(Scene scene) {
        System.out.println(scene);
        gameCarousel = (HBox)scene.lookup("#gameCarousel");
        watchTrailer = (Button)scene.lookup("#watchTrailer");
        enlargeImage = (HBox)scene.lookup("#enlargeImage");
        StrandedAwayBTN = (HBox)scene.lookup("#StrandedAwayBTN");
        DogeBTN = (HBox)scene.lookup("#DogeBTN");
        gameTitle = (Pane)scene.lookup("#gameTitle");
        gameCover = (StackPane)scene.lookup("#gameCover");

        enlargeImage.setOnMouseClicked(event -> enlargeImage.getParent().setVisible(false));
        StrandedAwayBTN.setOnMouseClicked(event -> changeGame("StrandedAway"));
        DogeBTN.setOnMouseClicked(event -> changeGame("Doge"));

        updateCarousel(new String[]{"https://picsum.photos/200/300", "https://picsum.photos/200/300", "https://picsum.photos/200/300"});
        updateTrailer("https://youtu.be/FB92RX_obXA");
    }
    private static void updateCarousel(String[] array) {
        gameCarousel.getChildren().clear();

        for (int i = 0; i < array.length; i++) {
            ImageView imageView = new ImageView();

            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setFitHeight(200);
            imageView.setCursor(Cursor.HAND);
            imageView.setOpacity(0.75);
            HBox.setMargin(imageView, new Insets(0, 8, 0, 0));
            imageView.setOnMouseEntered(event -> imageView.setOpacity(1));
            imageView.setOnMouseExited(event -> imageView.setOpacity(0.75));

            int finalI = i;
            imageView.setOnMouseClicked(event -> zoomImage(String.valueOf(array[finalI])));

            imageView.setImage(new Image(array[i]));
            gameCarousel.getChildren().add(imageView);
        }
    }
    private static void updateTrailer(String link) {
        watchTrailer.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private static void zoomImage(String image) {
        enlargeImage.getParent().setVisible(true);

        enlargeImage.setStyle("-fx-background-image: url('" + image + "');" +
                "-fx-background-size: contain;" +
                "-fx-background-position: top center;" +
                "-fx-background-repeat: no-repeat;");
    }
    private static void changeGame(String name) {
        switch (name) {
            case "StrandedAway":
                System.out.println(name);
                StrandedAwayBTN.getStyleClass().add("strandedAwayIconSelected");
                DogeBTN.getStyleClass().remove("dogeIconSelected");
                DogeBTN.getStyleClass().remove("dogeIconSelected");
                gameTitle.setStyle("-fx-background-image: url('images/gameTitles/StrandedAwayTitle.png');");
                gameCover.setStyle("-fx-background-image: url('images/gameCovers/StrandedAwayCover.png');");
                break;
            case "Doge":
                System.out.println(name);
                DogeBTN.getStyleClass().add("dogeIconSelected");
                StrandedAwayBTN.getStyleClass().remove("strandedAwayIconSelected");
                StrandedAwayBTN.getStyleClass().remove("strandedAwayIconSelected");
                gameTitle.setStyle("-fx-background-image: url('images/gameTitles/DogeTitle.png');");
                gameCover.setStyle("-fx-background-image: url('images/gameCovers/DogeCover.png');");
                break;
        }
    }
}
