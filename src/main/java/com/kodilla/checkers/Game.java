package com.kodilla.checkers;


import com.kodilla.checkers.model.Field;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;


import static com.kodilla.checkers.Checkers.*;
import static com.kodilla.checkers.logic.Controller.*;

import static com.kodilla.checkers.model.Texts.nameOfTheGame;
import static com.kodilla.checkers.utils.Constants.*;

/*
this class controls launching a game
 */
public class Game extends Application {

    private Checkers checkers = new Checkers();
    private Scene scene = new Scene(checkers.createBoard(false));

    private final File savedGame = new File(SAVE_FILE);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(nameOfTheGame);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        btnNewGame.setOnAction(event -> {
            refreshStateOfGame();
            checkers = new Checkers();
            scene = new Scene(checkers.createBoard(false));

            start(primaryStage);
        });

        btnSaveGame.setOnAction(event -> saveGame());

        btnContinueGame.setOnAction(event -> {
            checkers = new Checkers();
            scene = new Scene(checkers.createBoard(true));

            start(primaryStage);
        });

        btnSaveCloseGame.setOnAction(event -> {
            saveGame();
            primaryStage.close();
        });

        btnCloseGame.setOnAction(event -> primaryStage.close());

    }

    /*
     * cleaning data in objects
     */
    void refreshStateOfGame() {
        userRed.cleanUp();
        userWhite.cleanUp();
        game.cleanStateOfGame();
        rightToMove.cleanRightToMove();
    }

    /*
    trying to write state of game in a file
     */
    void saveGame() {
        try {
            FileOutputStream fos = new FileOutputStream(savedGame);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userRed);
            oos.writeObject(userWhite);
            oos.writeObject(game);
            oos.writeObject(rightToMove);


            for (Field field : getListOfFields()) {

                oos.writeObject(field);
            }

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
