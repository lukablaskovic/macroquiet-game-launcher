package com.example.demo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class SceneChanger {
    public static HBox gameCarousel;
    public static void setup(Scene scene) {
        System.out.println(scene);
        gameCarousel = (HBox)scene.lookup("#gameCarousel");

        updateCarousel();
    }
    private static void updateCarousel() {
        gameCarousel.getChildren().clear();

        String[] array = {"https://picsum.photos/200/300", "https://picsum.photos/200/300", "https://picsum.photos/200/300"};

        for (int i = 0; i < array.length; i++) {
            ImageView imageView = new ImageView();

            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setFitHeight(200);
            HBox.setMargin(imageView, new Insets(0, 8, 0, 0));

            imageView.setImage(new Image(array[i]));
            gameCarousel.getChildren().add(imageView);
        }
    }
}
