package com.kodilla.checkers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Checkers extends Application {

    private Image board = new Image("file:src/main/resources/table.png");
    private Image whitePawn = new Image("file:src/main/resources/white.png");
    private Image blackPawn = new Image("file:src/main/resources/black.png");


    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane gridMain = new GridPane();
        gridMain.setAlignment(Pos.TOP_LEFT);
        gridMain.setPadding(new Insets(145, 0, 0, 425));
        gridMain.setHgap(9);
        gridMain.setVgap(9);

        gridMain.setGridLinesVisible(true);

        gridMain.setBackground(background);

        ImageView imgPawnWhite0 = new ImageView(whitePawn);
        ImageView imgPawnWhite1 = new ImageView(whitePawn);
        ImageView imgPawnWhite2 = new ImageView(whitePawn);
        ImageView imgPawnWhite3 = new ImageView(whitePawn);
        ImageView imgPawnWhite4 = new ImageView(whitePawn);
        ImageView imgPawnBlack = new ImageView(blackPawn);



        gridMain.add(imgPawnWhite0, 0, 0, 2, 1);
        gridMain.add(imgPawnWhite1, 2, 0, 2, 1);
        gridMain.add(imgPawnBlack, 1 , 0, 1, 1);
//        gridMain.add(imgPawnWhite3, 9, 0, 1, 1);
//        gridMain.add(imgPawnWhite4, 12, 0, 1, 1);
        //gridMain.add(imgPawnWhite, 0, 0, 1, 1);

        Scene scene = new Scene(gridMain, 1200, 600, Color.BLACK);

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
