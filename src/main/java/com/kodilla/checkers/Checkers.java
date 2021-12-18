package com.kodilla.checkers;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.kodilla.checkers.Controller.switchTurn;
import static com.kodilla.checkers.PieceType.RED;
import static com.kodilla.checkers.PieceType.WHITE;



public class Checkers extends Application {

    public static final String COLOR_BLUE = "-fx-background-color : #0000FF;";
    public static final String COLOR_WHITE = "-fx-background-color : #FFFFF0;";
    private final Image board = new Image("file:src/main/resources/table.png");
    public static final double TILE_SIZE = 51.5;
    public static final int PADDING_X = 360;
    public static final int PADDING_Y = 140;
    public static final int HEIGHT = 8;
    public static final int WIDTH = 8;


    private Group fields = new Group();
    private Group pieces = new Group();
    private GridPane grid = new GridPane();

    private Label turn = new Label("Twoj ruch:");
    private Label user = new Label("Bialy");

    public static Field[][] table = new Field[WIDTH][HEIGHT];
    private int counterOfPieces = 0;

    public static User userRed = new User("Czerwony", RED, false, false);
    public static User userWhite = new User("Bialy", WHITE, false, true);



    private Parent createTable() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Pane root = new Pane();
        root.setPrefSize(1118, 616);
        root.setBackground(background);
        root.getChildren().addAll(fields, pieces, grid);

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
                Piece piece = null;
                if (row < 3 && ((col + row) % 2) != 0) {
                    piece = makePiece(RED, col, row, counterOfPieces);
                    counterOfPieces++;
                }
                if (row > 4 && ((col + row) % 2) != 0) {
                    piece = makePiece(WHITE, col, row, counterOfPieces);
                    counterOfPieces++;
                }
                if (piece !=null) {
                    field.setPiece(piece);
                    pieces.getChildren().add(piece);
                }
                table[col][row]= field;
            }
        }

        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(100));
        grid.setHgap(20);
        grid.setVgap(30);
        Font font1 = new Font("Arial", 40);
        turn.setFont(font1);
        user.setFont(font1);
        turn.setTextFill(Color.WHITE);
        user.setTextFill(Color.WHITE);
        grid.add(turn, 0,0);
        grid.add(user, 0, 1);


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

    private Piece makePiece(PieceType pieceType, int col, int row, int counterOfPieces) {
        Piece piece = new Piece(pieceType, col, row, counterOfPieces);


        piece.setOnMouseReleased(event -> {
            int oldCol = piece.getCol();
            int oldRow = piece.getRow();
            int newCol =(int) ((piece.getLayoutX()+(TILE_SIZE/2))/TILE_SIZE);
            int newRow = (int) ((piece.getLayoutY()+(TILE_SIZE/2))/TILE_SIZE);
            System.out.println("newCOl " + newCol + ", newRow: " + newRow);
            MoveType result = Controller.INSTANCE.checkMove(piece, newCol, newRow);

            if (result == MoveType.FORBIDDEN) {
                piece.relocate(oldCol*TILE_SIZE, oldRow*TILE_SIZE);
            } else if (result == MoveType.NORMAL) {

                piece.relocate(newCol * TILE_SIZE, newRow * TILE_SIZE);
                piece.setCol(newCol);
                piece.setRow(newRow);
                table[oldCol][oldRow].setPiece(null);
                table[newCol][newRow].setPiece(piece);
                user.setText(switchTurn(piece, false));
                System.out.println("table new: " + table[newCol][newRow].toString() + "table old: "+ table[oldCol][oldRow].toString());
            } else if (result == MoveType.KILLING) {
                piece.relocate(newCol * TILE_SIZE, newRow * TILE_SIZE);
                piece.setCol(newCol);
                piece.setRow(newRow);
                table[oldCol][oldRow].setPiece(null);
                table[newCol][newRow].setPiece(piece);
                int neighborCol = (newCol + oldCol) / 2;
                int neighborRow = (newRow + oldRow) / 2;
                System.out.println("neibCol " + neighborCol + " neibRow " + neighborRow);

                pieces.getChildren().remove(table[neighborCol][neighborRow].getPiece());
                table[neighborCol][neighborRow].setPiece(null);
                user.setText(switchTurn(piece, true));
            }
        });

        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
