package com.kodilla.checkers;

import javafx.application.Application;

import javafx.geometry.HPos;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static com.kodilla.checkers.Constants.*;
import static com.kodilla.checkers.Controller.movementSummary;
import static com.kodilla.checkers.PawnType.RED;
import static com.kodilla.checkers.PawnType.WHITE;


public class Checkers extends Application {

    private final Image board = new Image("file:src/main/resources/table.png");
    public static User userRed = new User(Texts.reds, RED, false);
    public static User userWhite = new User(Texts.whites, WHITE, false);

    public static RightToMove rightToMove = new RightToMove();

    private Group fields = new Group();
    private Group pawns = new Group();
    private GridPane grid = new GridPane();

    private Label inviting = new Label(Texts.nameOfTheGame);
    private Label redName = new Label(Texts.reds);
    private Label whiteName = new Label(Texts.whites);
    private Label whoMove = new Label(rightToMove.getUserToMove() + " have to move");
    private Label numbRedPawnsDescribing = new Label(Texts.numbPawns);
    private Label numbWhitePawnsDescribing = new Label(Texts.numbPawns);
    private Label numbRedPawns = new Label(String.valueOf(userRed.getNumbOfPawns()));
    private Label numbWhitePawns = new Label(String.valueOf(userWhite.getNumbOfPawns()));



    public static Field[][] table = new Field[WIDTH][HEIGHT];
    private int pawnNumber = 0;




    private Parent createBoard() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(board, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Pane root = new Pane();
        root.setPrefSize(1118, 616); //size same as background image
        root.setBackground(background);
        root.getChildren().addAll(fields, pawns, grid);
/**
 * padding according to background image
 */
        fields.setLayoutX(PADDING_X);
        fields.setLayoutY(PADDING_Y);
        String color;
        pawns.setLayoutX(PADDING_X);
        pawns.setLayoutY(PADDING_Y);

        /**
         * creating board with pawns in the beginning
         */
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if ((col + row) % 2 != 0) {
                    color = COLOR_BLUE;
                } else {
                    color = COLOR_WHITE;
                }
                Field field = new Field(col, row, color);
                fields.getChildren().add(field);
                Pawn pawn = null;
                /**
                 * pawns only on blue fields
                 */
                if (row < 3 && ((col + row) % 2) != 0) {
                    pawn = makePawn(RED, col, row, pawnNumber);
                    pawnNumber++;
                }
                if (row > 4 && ((col + row) % 2) != 0) {
                    pawn = makePawn(WHITE, col, row, pawnNumber);
                    pawnNumber++;
                }
                if (pawn !=null) {
                    field.setPawn(pawn);
                    pawns.getChildren().add(pawn);
                }
                table[col][row]= field;
            }
        }
/**
 * the grid with any messages and notifications
 */
        //grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 50, 80, 100));
        grid.setHgap(20);
        grid.setVgap(10);

        //columns
        ColumnConstraints columnLeft = new ColumnConstraints(200);
        ColumnConstraints columnMiddle = new ColumnConstraints(500);
        ColumnConstraints columnRight = new ColumnConstraints(200);
        columnLeft.setHalignment(HPos.CENTER);
        columnMiddle.setHalignment(HPos.CENTER);
        columnRight.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(columnLeft);
        grid.getColumnConstraints().add(columnMiddle);
        grid.getColumnConstraints().add(columnRight);

        Font font1 = new Font("Arial", 40);
        Font font2 = new Font("Arial", 30);
        Font font3 = new Font("Arial", 20);

        inviting.setFont(font1);
        inviting.setTextAlignment(TextAlignment.CENTER);
        redName.setFont(font2);
        whiteName.setFont(font2);
        whoMove.setFont(font2);
        whoMove.setTextAlignment(TextAlignment.CENTER);
        numbRedPawnsDescribing.setFont(font3);
        numbWhitePawnsDescribing.setFont(font3);
        numbWhitePawns.setFont(font3);
        numbRedPawns.setFont(font3);

        inviting.setTextFill(Color.YELLOW);
        redName.setTextFill(Color.YELLOW);
        whiteName.setTextFill(Color.YELLOW);
        whoMove.setTextFill(Color.YELLOW);
        numbRedPawnsDescribing.setTextFill(Color.YELLOW);
        numbWhitePawnsDescribing.setTextFill(Color.YELLOW);
        numbRedPawns.setTextFill(Color.YELLOW);
        numbWhitePawns.setTextFill(Color.YELLOW);
        inviting.setTextFill(Color.BLUE);
        redName.setTextFill(Color.RED);
        whiteName.setTextFill(Color.WHITE);

        grid.add(inviting, 1,0);
        grid.add(whoMove, 1,1);
        grid.add(redName, 0, 2);
        grid.add(whiteName, 2, 2);
        grid.add(numbRedPawnsDescribing, 0,3);
        grid.add(numbWhitePawnsDescribing, 2,3);
        grid.add(numbRedPawns, 0, 4);
        grid.add(numbWhitePawns, 2,4);

        grid.setHalignment(inviting, HPos.CENTER);
        grid.setHalignment(whoMove, HPos.CENTER);

        grid.setMouseTransparent(true);


        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createBoard());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Pawn makePawn(PawnType pawnType, int col, int row, int pieceNumber) {

        Pawn pawn = new Pawn(pawnType, col, row, pieceNumber);
/**
 * the last stage of moving the pawn, previous stages are included in Pawn class
 */
        pawn.setOnMouseReleased(event -> {
            int oldCol = pawn.getCol();
            int oldRow = pawn.getRow();
            int newCol =(int) ((pawn.getLayoutX()+(TILE_SIZE/2))/TILE_SIZE);
            int newRow = (int) ((pawn.getLayoutY()+(TILE_SIZE/2))/TILE_SIZE);
            System.out.println("newCOl " + newCol + ", newRow: " + newRow);
            MoveType result = Controller.INSTANCE.checkMove(pawn, newCol, newRow);

            if (result == MoveType.FORBIDDEN) {
                pawn.relocate(oldCol*TILE_SIZE, oldRow*TILE_SIZE);

            } else if (result == MoveType.NORMAL) {

                pawn.relocate(newCol * TILE_SIZE, newRow * TILE_SIZE);
                pawn.setCol(newCol);
                pawn.setRow(newRow);
                table[oldCol][oldRow].setPawn(null);
                table[newCol][newRow].setPawn(pawn);
                whoMove.setText(movementSummary(pawn, false));
                System.out.println("table new: " + table[newCol][newRow].toString() + "table old: "+ table[oldCol][oldRow].toString());

            } else if (result == MoveType.KILLING) {
                pawn.relocate(newCol * TILE_SIZE, newRow * TILE_SIZE);
                pawn.setCol(newCol);
                pawn.setRow(newRow);
                table[oldCol][oldRow].setPawn(null);
                table[newCol][newRow].setPawn(pawn);
                int neighborCol = (newCol + oldCol) / 2;
                int neighborRow = (newRow + oldRow) / 2;
                System.out.println("neibCol " + neighborCol + " neibRow " + neighborRow);

                pawns.getChildren().remove(table[neighborCol][neighborRow].getPawn());
                table[neighborCol][neighborRow].setPawn(null);
                whoMove.setText(movementSummary(pawn, true));
                numbWhitePawns.setText(String.valueOf(userWhite.getNumbOfPawns()));
                numbRedPawns.setText(String.valueOf(userRed.getNumbOfPawns()));
            }
        });

        return pawn;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
