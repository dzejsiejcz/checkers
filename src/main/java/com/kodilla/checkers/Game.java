package com.kodilla.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static com.kodilla.checkers.model.Texts.nameOfTheGame;

public class Game extends Application {
    private Button btnNewGame = new Button("New Game");

    @Override
    public void start(Stage primaryStage) {
        Checkers checkers = new Checkers();
        Scene scene = new Scene(checkers.createBoard());

        primaryStage.setTitle(nameOfTheGame);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        //primaryStage.add(btnNewGame);
        primaryStage.show();

//        btnNewGame.setOnAction(e -> {
//            primaryStage.close();
//            cleanup();
//            Scene scene2 = new Scene(createBoard());
//            primaryStage.setTitle(CHECKERS);
//            primaryStage.setScene(scene2);
//            primaryStage.setResizable(false);
//            primaryStage.show();
//
//        });

    }

    void cleanup() {
//        grid.getChildren().removeAll();
//        fields.getChildren().removeAll();
//        pawns.getChildren().removeAll();
//        buttonsGrid.getChildren().removeAll();
//        root.getChildren().removeAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
