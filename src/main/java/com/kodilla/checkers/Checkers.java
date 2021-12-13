package com.kodilla.checkers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javafx.stage.Stage;

import static com.kodilla.checkers.PieceType.RED;
import static com.kodilla.checkers.PieceType.WHITE;



public class Checkers extends Application {

    public static final String COLOR_BLUE = "-fx-background-color : #0000FF;";
    public static final String COLOR_WHITE = "-fx-background-color : #FFFFF0;";
    private Image board = new Image("file:src/main/resources/table.png");
    public static final double TILE_SIZE = 51.5;
    public static final double PADDING_X = 360;
    public static final double PADDING_Y = 140;
    public static final int HEIGHT = 8;
    public static final int WIDTH = 8;


    private Group fields = new Group();
    private Group pieces = new Group();

    public Field[][] table = new Field[WIDTH][HEIGHT];


    private Parent createTable() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Pane root = new Pane();
        root.setPrefSize(1118, 616);
        root.setBackground(background);
        root.getChildren().addAll(fields, pieces);

        fields.setLayoutX(PADDING_X);
        fields.setLayoutY(PADDING_Y);

        String color;

        pieces.setLayoutX(PADDING_X);
        pieces.setLayoutY(PADDING_Y);

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if ((col + row) % 2 != 0) {
                    color = COLOR_BLUE;
                } else {
                    color = COLOR_WHITE;
                }
                Field field = new Field(col, row, color);
                fields.getChildren().add(field);
                table[col][row]= field;

                Piece piece = null;
                if (row < 3 && ((col + row) % 2) != 0) {
                    piece = makePiece(RED, col, row);
                }
                if (row > 4 && ((col + row) % 2) != 0) {
                    piece = makePiece(WHITE, col, row);
                }
                if (piece !=null) {
                    field.setPiece(piece);
                    pieces.getChildren().add(piece);
                }
            }
        }
        return root;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(createTable());

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Piece makePiece(PieceType pieceType, int col, int row) {
        Piece piece = new Piece(pieceType, col, row);


        piece.setOnMouseReleased(event ->{
            int newCol =(int) ((piece.getLayoutX()+(TILE_SIZE/2))/TILE_SIZE);
            int newRow = (int) ((piece.getLayoutY()+(TILE_SIZE/2))/TILE_SIZE);

            piece.relocate(newCol*TILE_SIZE, newRow*TILE_SIZE);
            piece.setCol(newCol);
            piece.setRow(newRow);
            System.out.println(piece.getCol());
            System.out.println(piece.getRow());
        });
        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
