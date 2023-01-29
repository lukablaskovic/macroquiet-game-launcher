package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class SceneChanger {
    public static HBox gameCarousel;
    public static Button watchTrailer;
    public static HBox enlargeImage;
    public static HBox StrandedAwayBTN;
    public static HBox DogeBTN;
    public static Pane gameTitle;
    public static StackPane gameCover;
    public static VBox systemRequirements;
    public static Button systemRequirementsBTN;
    public static SplitPane systemRequirementsSplitPane;
    public static Text aboutGame;
    public static Pane ratingIMG;
    public static FlowPane ratingDesc;
    public static Button loginBTN;
    public static Label username;

    public static ArrayList<String> dogeCarouselImages = new ArrayList<>();
    public static void setup(Scene scene, Stage stage) {
        System.out.println(scene);
        gameCarousel = (HBox)scene.lookup("#gameCarousel");
        watchTrailer = (Button)scene.lookup("#watchTrailer");
        enlargeImage = (HBox)scene.lookup("#enlargeImage");
        StrandedAwayBTN = (HBox)scene.lookup("#StrandedAwayBTN");
        DogeBTN = (HBox)scene.lookup("#DogeBTN");
        gameTitle = (Pane)scene.lookup("#gameTitle");
        gameCover = (StackPane)scene.lookup("#gameCover");
        systemRequirements = (VBox)scene.lookup("#systemRequirements");
        systemRequirementsBTN = (Button)scene.lookup("#systemRequirementsBTN");
        systemRequirementsSplitPane = (SplitPane)scene.lookup("#systemRequirementsSplitPane");
        aboutGame = (Text)scene.lookup("#aboutGame");
        ratingIMG = (Pane)scene.lookup("#ratingIMG");
        System.out.println(ratingIMG);
        ratingDesc = (FlowPane)scene.lookup("#ratingDesc");
        loginBTN = (Button)scene.lookup("#loginBTN");
        username = (Label)scene.lookup("#username");
        if (LogInChanger.receivedCredentials != null) {
            username.setText(LogInChanger.receivedCredentials.getString("username"));
            loginBTN.setText("SIGN OUT");
            loginBTN.setOnAction(event -> {
                LogInChanger.receivedCredentials = null;
                username.setText("");
                loginBTN.setText("LOG IN");
                setLogin(stage);
            });
        }
        else {
            setLogin(stage);
        }

        enlargeImage.setOnMouseClicked(event -> enlargeImage.getParent().setVisible(false));
        StrandedAwayBTN.setOnMouseClicked(event -> changeGame("StrandedAway"));
        DogeBTN.setOnMouseClicked(event -> changeGame("Doge"));
        systemRequirementsBTN.setOnAction(event -> toggleSystemRequirements(systemRequirementsSplitPane));

        toggleSystemRequirements(systemRequirementsSplitPane);

        updateCarousel(MainApplication.carouselURLs);
        updateTrailer("https://youtu.be/FB92RX_obXA");
        setSystemRequirements(new String[] {
                "4 core CPU",
                "4 GB RAM",
                "Windows, macOS, Linux",
                "Graphics: nVidia GeForce GTX 660 2GB/ AMD Radeon HD 7850 2gb",
                "Processor: Intel Core i3-4340 / AMD FX-6300",
                "Memory: 4 GB RAM",
                "DirectX: Version 11",
                "OS: 64-bit Windows, macOS and Linux systems",
                "Storage: 512MB" });
        if (dogeCarouselImages.size() == 0) {
            dogeCarouselImages = new ArrayList<>();
            dogeCarouselImages.add("https://i.imgur.com/moEPV2p.png");
            dogeCarouselImages.add("https://i.imgur.com/aMFAuhI.png");
            dogeCarouselImages.add("https://i.imgur.com/QOce60G.png");
            dogeCarouselImages.add("https://i.imgur.com/WuPSo9r.png");
            dogeCarouselImages.add("https://i.imgur.com/dPvji1j.png");
            dogeCarouselImages.add("https://i.imgur.com/0itNd3z.png");
            dogeCarouselImages.add("https://i.imgur.com/P5fXLlQ.png");
            dogeCarouselImages.add("https://i.imgur.com/ySVRuUT.png");
            dogeCarouselImages.add("https://i.imgur.com/nWlSUNs.png");
        }
    }
    private static void setLogin(Stage stage) {
        loginBTN.setOnAction(event -> {
            try {
                switchToScene(stage, "logIn-view");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private static void updateCarousel(ArrayList<String> array) {
        gameCarousel.getChildren().clear();

        for (int i = 0; i < array.size(); i++) {
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
            imageView.setOnMouseClicked(event -> zoomImage(String.valueOf(array.get(finalI))));

            imageView.setImage(new Image(array.get(i), true));
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
    private static void setRatingDesc(String[] array) {
        ratingDesc.getChildren().clear();
        for (int i = 0; i < array.length; i++) {
            Text text = new Text();
            text.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            text.setFill(Color.web("#e7e5e4"));
            text.setText(array[i]);
            ratingDesc.getChildren().add(text);
        }
    }
    private static void setSystemRequirements(String[] array) {
        systemRequirements.getChildren().clear();
        for (int i = 0; i < array.length; i++) {
            Text text = new Text();
            text.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            text.setFill(Color.web("#e7e5e4"));
            text.setText("• "+array[i]);
            systemRequirements.getChildren().add(text);
        }
    }
    private static void toggleSystemRequirements(SplitPane splitPane) {
        Timeline timeline = new Timeline();
        KeyValue kv;
        if (splitPane.getDividerPositions()[0] > 0.9) {
            kv = new KeyValue(splitPane.getDividers().get(0).positionProperty(), 0);
        } else {
            kv = new KeyValue(splitPane.getDividers().get(0).positionProperty(), 1);
        }
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    private static void changeGame(String name) {
        switch (name) {
            case "StrandedAway":
                StrandedAwayBTN.getStyleClass().add("strandedAwayIconSelected");
                DogeBTN.getStyleClass().remove("dogeIconSelected");
                DogeBTN.getStyleClass().remove("dogeIconSelected");
                gameTitle.setStyle("-fx-background-image: url('images/gameTitles/StrandedAwayTitle.png');");
                gameCover.setStyle("-fx-background-image: url('images/gameCovers/StrandedAwayCover.png');");
                aboutGame.setText("Stranded Away is a 2D pixel art singleplayer: platform-jumper, puzzle and action game. " +
                        "You are playing as a mysterious space traveller who's looking for long gone inhabitants of planet Athion.");
                ratingIMG.getStyleClass().clear();
                ratingIMG.getStyleClass().add("ratingT");
                setRatingDesc(new String[] {"Fantasy Violence", "Animated Blood", "Use of Alcohol and Tobacco"});
                updateCarousel(MainApplication.carouselURLs);
                watchTrailer.setVisible(true);
                break;
            case "Doge":
                DogeBTN.getStyleClass().add("dogeIconSelected");
                StrandedAwayBTN.getStyleClass().remove("strandedAwayIconSelected");
                StrandedAwayBTN.getStyleClass().remove("strandedAwayIconSelected");
                gameTitle.setStyle("-fx-background-image: url('images/gameTitles/DogeTitle.png');");
                gameCover.setStyle("-fx-background-image: url('images/gameCovers/DogeCover.png');");
                aboutGame.setText("Doge game is a small free-to-play 2.5D platform jumper about a lost doge who tries to find his way " +
                        "home. He is roaming across the streets, crossing roads, jumping cars and running away from nasty dog catchers! " +
                        "Play now and help doge find his way home!");
                ratingIMG.getStyleClass().clear();
                ratingIMG.getStyleClass().add("ratingE");
                setRatingDesc(new String[] {"Comic Mischief", "Mild Lyrics"});
                updateCarousel(dogeCarouselImages);
                watchTrailer.setVisible(false);
                break;
        }
    }
    public static void switchToScene(Stage stage, String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        LogInChanger.setup(scene, stage);
    }
}
