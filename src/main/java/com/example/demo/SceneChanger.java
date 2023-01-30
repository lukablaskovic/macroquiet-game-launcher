package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class SceneChanger {
    public static HBox gameCarousel;
    public static Button watchTrailer;
    public static Button play;
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
    public static Pane playIMG;
    public static FlowPane trophies;
    public static ArrayList<String> dogeCarouselImages = new ArrayList<>();
    public static ArrayList<String> strandedAwayCarouselImages = new ArrayList<>();
    public static JSONArray userTrophies;
    public static void setup(Scene scene, Stage stage) throws InterruptedException {
        trophies = (FlowPane)scene.lookup("#trophies");
        play = (Button)scene.lookup("#play");
        gameCarousel = (HBox)scene.lookup("#gameCarousel");
        watchTrailer = (Button)scene.lookup("#watchTrailer");
        enlargeImage = (HBox)scene.lookup("#enlargeImage");
        StrandedAwayBTN = (HBox)scene.lookup("#StrandedAwayBTN");
        DogeBTN = (HBox)scene.lookup("#DogeBTN");
        gameTitle = (Pane)scene.lookup("#gameTitle");
        playIMG = (Pane)scene.lookup("#playIMG");
        gameCover = (StackPane)scene.lookup("#gameCover");
        systemRequirements = (VBox)scene.lookup("#systemRequirements");
        systemRequirementsBTN = (Button)scene.lookup("#systemRequirementsBTN");
        systemRequirementsSplitPane = (SplitPane)scene.lookup("#systemRequirementsSplitPane");
        aboutGame = (Text)scene.lookup("#aboutGame");
        ratingIMG = (Pane)scene.lookup("#ratingIMG");
        ratingDesc = (FlowPane)scene.lookup("#ratingDesc");
        loginBTN = (Button)scene.lookup("#loginBTN");
        username = (Label)scene.lookup("#username");

        enlargeImage.setOnMouseClicked(event -> enlargeImage.getParent().setVisible(false));
        StrandedAwayBTN.setOnMouseClicked(event -> {
            try {
                changeGame("StrandedAway");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        DogeBTN.setOnMouseClicked(event -> {
            try {
                changeGame("Doge");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        systemRequirementsBTN.setOnAction(event -> toggleSystemRequirements(systemRequirementsSplitPane));

        if (LogInChanger.receivedCredentials != null) {
            username.setText(LogInChanger.receivedCredentials.getString("username"));
            loginBTN.setText("SIGN OUT");
            loginBTN.setOnAction(event -> {
                LogInChanger.receivedCredentials = null;
                username.setText("");
                loginBTN.setText("LOG IN");
                setLogin(stage);
                trophies.getChildren().clear();
            });
        }
        else {
            setLogin(stage);
        }
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
        if (strandedAwayCarouselImages.size() == 0) {
            strandedAwayCarouselImages = new ArrayList<>();
            strandedAwayCarouselImages.add("https://i.imgur.com/BXNJVYL.png");
            strandedAwayCarouselImages.add("https://i.imgur.com/HNZYFdv.png");
            strandedAwayCarouselImages.add("https://i.imgur.com/JzMWJmA.png");
            strandedAwayCarouselImages.add("https://i.imgur.com/iVkkdss.png");
            strandedAwayCarouselImages.add("https://i.imgur.com/Gq1G9wf.png");
            strandedAwayCarouselImages.add("https://i.imgur.com/gqFiuIQ.png");
            strandedAwayCarouselImages.add("https://i.imgur.com/OCNDGy4.png");
            strandedAwayCarouselImages.add("https://i.imgur.com/SPgkpE7.png");
        }
        toggleSystemRequirements(systemRequirementsSplitPane);
        changeGame("StrandedAway");
    }
    private static void loadTrophies() {
        trophies.getChildren().clear();
        for (int i = 0; i < userTrophies.length(); i++) {

            JSONObject object = userTrophies.getJSONObject(i);
            String extractedSpriteName = object.getString("spriteName");
            String extractedTrophieName = object.getString("trophieName");
            Boolean extractedUnlocked = object.getBoolean("unlocked");
            String extractedTrophieImage = extractedUnlocked ? extractedSpriteName : "hidden_trophy";

            VBox trophie = new VBox();
            trophie.setAlignment(Pos.TOP_CENTER);
            trophie.setFillWidth(true);
            trophie.setPrefWidth(134);
            trophie.setMaxWidth(134);

            TextField trophieName = new TextField();
            trophieName.setText(extractedTrophieName);
            trophieName.setAlignment(Pos.CENTER);
            trophieName.prefHeight(Region.USE_COMPUTED_SIZE);
            trophieName.prefWidth(Region.USE_COMPUTED_SIZE);
            trophieName.getStyleClass().add("px14");
            trophieName.getStyleClass().add("trophieLabel");
            trophieName.setOpacity(0);

            Pane trophieIMG = new Pane();
            trophieIMG.setPrefSize(134, 134);
            trophieIMG.setMaxWidth(134);
            trophieIMG.setCursor(Cursor.HAND);
            String temp = "trophie-"+extractedTrophieImage.replaceAll(" ", "").replaceAll(",", "");
            trophieIMG.getStyleClass().add(temp);

            trophieIMG.setOnMouseEntered(event -> {
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.25), trophieName);
                fadeTransition.setFromValue(trophieName.getOpacity());
                fadeTransition.setToValue(1);
                fadeTransition.play();
            });

            trophieIMG.setOnMouseExited(event -> {
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.25), trophieName);
                fadeTransition.setFromValue(trophieName.getOpacity());
                fadeTransition.setToValue(0);
                fadeTransition.play();
            });

            trophie.getChildren().add(trophieName);
            trophie.getChildren().add(trophieIMG);
            trophie.setMargin(trophieIMG, new Insets(8, 0, 0, 0));

            trophies.getChildren().add(trophie);
        }
    }
    private static void loadUserProfile(String username, String game) throws InterruptedException {
        String URL = String.format("https://macroquiet.herokuapp.com/unity/user/profile?username=%s", username);
        GetTask loadUserDataTask = new GetTask(URL, JSONObject.class);
        Thread loadUserDataThread = new Thread(loadUserDataTask);
        loadUserDataThread.start();
        loadUserDataThread.join();
        ArrayList<String> userData = loadUserDataTask.getResult();
        JSONObject user = new JSONObject(userData.get(0));

        JSONObject userProfile = user.getJSONObject("profile");
        JSONArray userGames = new JSONArray(userProfile.getJSONArray("games"));
        JSONObject userGame = new JSONObject();
        for (int i = 0; i < userGames.length(); i++) {
            JSONObject object = userGames.getJSONObject(i);
            if (object.getString("name").equals(game)) {
                userGame = object;
                break;
            }
        }
        userTrophies = new JSONArray(userGame.getJSONArray("trophies"));
        loadTrophies();
    }
    private static void checkGame(String gameName, String gameLink) {
        File file = new File("C:\\Program Files (x86)\\MacroQuiet\\"+gameName+"\\"+gameName+".exe");
        if (file.exists()) {
            play.setText("PLAY");
            playIMG.getStyleClass().remove("download");
            playIMG.getStyleClass().add("play");
            play.setOnAction(event -> {
                String[] cmd = {"C:\\Program Files (x86)\\MacroQuiet\\"+gameName+"\\"+gameName+".exe"};
                try {
                    new ProcessBuilder(cmd).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            });
        } else {
            play.setText("DOWNLOAD");
            playIMG.getStyleClass().remove("play");
            playIMG.getStyleClass().add("download");
            play.setOnAction(event -> {
                try {
                    Desktop.getDesktop().browse(new URI(gameLink));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
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
    private static void changeGame(String name) throws InterruptedException {
        switch (name) {
            case "StrandedAway":
                StrandedAwayBTN.getStyleClass().add("strandedAwayIconSelected");
                DogeBTN.getStyleClass().remove("dogeIconSelected");
                DogeBTN.getStyleClass().remove("dogeIconSelected");
                gameCover.getStyleClass().clear();
                gameTitle.getStyleClass().clear();
                gameCover.getStyleClass().add("images-gameCovers-StrandedAway");
                gameTitle.getStyleClass().add("images-gameTitles-StrandedAway");
                aboutGame.setText("Stranded Away is a 2D pixel art singleplayer: platform-jumper, puzzle and action game. " +
                        "You are playing as a mysterious space traveller who's looking for long gone inhabitants of planet Athion.");
                ratingIMG.getStyleClass().clear();
                ratingIMG.getStyleClass().add("ratingT");
                setRatingDesc(new String[] {"Fantasy Violence", "Animated Blood", "Use of Alcohol and Tobacco"});
                updateCarousel(strandedAwayCarouselImages);
                checkGame("Stranded Away", "https://macroquiet.itch.io/stranded-away");
                watchTrailer.setText("GAME TRAILER");
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
                if (LogInChanger.receivedCredentials != null)
                    loadUserProfile(LogInChanger.receivedCredentials.getString("username"), "Stranded Away");
                break;
            case "Doge":
                DogeBTN.getStyleClass().add("dogeIconSelected");
                StrandedAwayBTN.getStyleClass().remove("strandedAwayIconSelected");
                StrandedAwayBTN.getStyleClass().remove("strandedAwayIconSelected");
                gameCover.getStyleClass().clear();
                gameTitle.getStyleClass().clear();
                gameCover.getStyleClass().add("images-gameCovers-Doge");
                gameTitle.getStyleClass().add("images-gameTitles-Doge");
                aboutGame.setText("Doge game is a small free-to-play 2.5D platform jumper about a lost doge who tries to find his way " +
                        "home. He is roaming across the streets, crossing roads, jumping cars and running away from nasty dog catchers! " +
                        "Play now and help doge find his way home!");
                ratingIMG.getStyleClass().clear();
                ratingIMG.getStyleClass().add("ratingE");
                setRatingDesc(new String[] {"Comic Mischief", "Mild Lyrics"});
                updateCarousel(dogeCarouselImages);
                checkGame("Doge", "https://macroquiet.itch.io/doge");
                watchTrailer.setText("GAME OST");
                updateTrailer("https://www.youtube.com/watch?v=kkkRJm4rJuI&list=PLKpWbF6ulKiBcRD6wzN4LdyzZnR-idZ-2");
                setSystemRequirements(new String[] {
                        "4 core CPU",
                        "4 GB RAM",
                        "Windows, macOS, Linux",
                        "Graphics: GeForce 8600 GT / Radeon HD 2600",
                        "Processor: Intel Core i3-4340 / AMD FX-6300",
                        "Memory: 4 GB RAM",
                        "DirectX: Version 11",
                        "OS: 64-bit Windows, macOS and Linux systems",
                        "Storage: 1GB" });
                if (LogInChanger.receivedCredentials != null)
                    loadUserProfile(LogInChanger.receivedCredentials.getString("username"), "Doge");
                break;
        }
    }
    public static void switchToScene(Stage stage, String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name+".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), stage.getWidth()-16, stage.getHeight()-39);
        stage.setScene(scene);
        stage.show();
        LogInChanger.setup(scene, stage);
    }
}
