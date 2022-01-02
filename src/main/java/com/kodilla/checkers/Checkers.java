package com.kodilla.checkers;

import static com.kodilla.checkers.logic.Controller.*;
import static com.kodilla.checkers.model.Texts.toMove;
import static com.kodilla.checkers.utils.Constants.*;
import static com.kodilla.checkers.utils.PawnType.RED;
import static com.kodilla.checkers.utils.PawnType.WHITE;

import com.kodilla.checkers.logic.Controller;
import com.kodilla.checkers.logic.RightToMove;
import com.kodilla.checkers.logic.StateOfGame;
import com.kodilla.checkers.model.Field;
import com.kodilla.checkers.model.Pawn;
import com.kodilla.checkers.model.Texts;
import com.kodilla.checkers.model.User;
import com.kodilla.checkers.utils.MoveType;
import com.kodilla.checkers.utils.PawnType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.*;

/*
this class creates a play board
 */

public class Checkers implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static User userRed = new User(Texts.reds, RED, false);
    public static User userWhite = new User(Texts.whites, WHITE, false);
    public static RightToMove rightToMove = new RightToMove();
    public static Field[][] table = new Field[WIDTH][HEIGHT];
    public static StateOfGame game = new StateOfGame();

    public static Button btnNewGame = new Button("New game");
    public static Button btnSaveGame = new Button("Save game");
    public static Button btnContinueGame = new Button("Continue saved game");
    public static Button btnSaveCloseGame = new Button("Save and Exit");
    public static Button btnCloseGame = new Button("Exit without saving");

    private final Label whoMove = new Label(rightToMove.getUserToMove() + " have to move");
    private final Label numbRedPawnsDescribing = new Label(Texts.numbPawns);
    private final Label numbWhitePawnsDescribing = new Label(Texts.numbPawns);
    private final Label numbRedPawns = new Label(String.valueOf(userRed.getNumbOfPawns()));
    private final Label numbWhitePawns = new Label(String.valueOf(userWhite.getNumbOfPawns()));

    private final Image board = new Image("file:src/main/resources/table.png");
    private final Group fields = new Group();
    private final Group pawns = new Group();
    private final GridPane grid = new GridPane();
    private final GridPane buttonsGrid = new GridPane();
    private final Pane root = new Pane();
    private final Label inviting = new Label(Texts.nameOfTheGame);
    private final Label redName = new Label(Texts.reds);
    private final Label whiteName = new Label(Texts.whites);

    private int pawnNumber = 0;

    private final Font arial40 = new Font("Arial", 40);
    private final Font arial30 = new Font("Arial", 30);
    private final Font arial20 = new Font("Arial", 20);


    public Checkers() {

    }

    public Parent createBoard(boolean fromFile) {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage =
                new BackgroundImage(board, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);


        root.setPrefSize(1118, 616); //size same as background image
        root.setBackground(background);
        root.getChildren().addAll(fields, pawns, grid, buttonsGrid);
        /*
         * padding according to background image
         */
        fields.setLayoutX(PADDING_X);
        fields.setLayoutY(PADDING_Y);
        pawns.setLayoutX(PADDING_X);
        pawns.setLayoutY(PADDING_Y);

        /*
         * creating board with pawns in the beginning
         */

        if (!fromFile) {

            /*
            variables for calculate numbers of Pawns, debuggers can change numbers of creating pawns
             */

            int numbPawnsLines = START_NUMBER_OF_PAWNS / 4;
            int startLineForReds = 3 - numbPawnsLines;
            int endLineForWhites = 4 + numbPawnsLines;


            System.out.println(numbPawnsLines);

            for (int col = 0; col < 8; col++) {
                for (int row = 0; row < 8; row++) {
                    String color;
                    if ((col + row) % 2 != 0) {
                        color = COLOR_BLUE;
                    } else {
                        color = COLOR_WHITE;
                    }
                    Field field = new Field(col, row, color);
                    fields.getChildren().add(field);
                    Pawn pawn = null;

                    /*
                     * pawns only on blue fields
                     */
                    if (row >= startLineForReds && row < 3 && ((col + row) % 2) != 0) {
                        pawn = makePawn(RED, col, row, pawnNumber);
                        pawnNumber++;
                    }
                    if (row <= endLineForWhites && row > 4 && ((col + row) % 2) != 0) {
                        pawn = makePawn(WHITE, col, row, pawnNumber);
                        pawnNumber++;
                    }
                    if (pawn != null) {
                        field.setPawn(pawn);
                        pawns.getChildren().add(pawn);
                    }
                    table[col][row] = field;
                }
            }

        } else {
            try {
                FileInputStream fis = new FileInputStream(SAVE_FILE);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userRed = (User) ois.readObject();
                System.out.println(userRed.toString());
                userWhite = (User) ois.readObject();
                System.out.println(userWhite.toString());
                game = (StateOfGame) ois.readObject();
                System.out.println(game.toString());
                rightToMove = (RightToMove) ois.readObject();
                System.out.println(rightToMove.toString());

                for (int col = 0; col < 8; col++) {
                    for (int row = 0; row < 8; row++) {
                        Field tempField = (Field) ois.readObject();
                        Pawn tempPawn = tempField.getPawn();
                        System.out.println("temp field: " + tempField);
                        String color = tempField.getColor();
                        Field reconstructedField = new Field(col, row, color);
                        if (tempPawn != null) {
                            PawnType type = tempPawn.getType();
                            int number = tempPawn.getPawnNumber();
                            Pawn reconstructedPawn = makePawn(type, col, row, number);
                            reconstructedField.setPawn(reconstructedPawn);
                            pawns.getChildren().add(reconstructedPawn);
                        }
                        fields.getChildren().add(reconstructedField);
                        System.out.println("rebuilt field: " + reconstructedField);
                        // tu był błąd - brakowało zapisu do table
                        table[col][row] = reconstructedField;
                    }
                }
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(game);
            String endResponse = game.whoWon();
            System.out.println(endResponse);


        }
        if (game.whoWon() != null) {
            whoMove.setText(game.whoWon());
        } else {
            whoMove.setText(rightToMove.getUserToMove() + toMove);
        }
        numbWhitePawns.setText(String.valueOf(userWhite.getNumbOfPawns()));
        numbRedPawns.setText(String.valueOf(userRed.getNumbOfPawns()));

        /*
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

        inviting.setFont(arial40);
        inviting.setTextAlignment(TextAlignment.CENTER);
        redName.setFont(arial30);
        whiteName.setFont(arial30);
        whoMove.setFont(arial30);
        whoMove.setTextAlignment(TextAlignment.CENTER);
        numbRedPawnsDescribing.setFont(arial20);
        numbWhitePawnsDescribing.setFont(arial20);
        numbWhitePawns.setFont(arial20);
        numbRedPawns.setFont(arial20);

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

        grid.add(inviting, 1, 0);
        grid.add(whoMove, 1, 1);
        grid.add(redName, 0, 2);
        grid.add(whiteName, 2, 2);
        grid.add(numbRedPawnsDescribing, 0, 3);
        grid.add(numbWhitePawnsDescribing, 2, 3);
        grid.add(numbRedPawns, 0, 4);
        grid.add(numbWhitePawns, 2, 4);

        GridPane.setHalignment(inviting, HPos.CENTER);
        GridPane.setHalignment(whoMove, HPos.CENTER);

        grid.setMouseTransparent(true);

        /*
         * the grid with buttons
         */
        //buttonsGrid.setGridLinesVisible(true);
        buttonsGrid.setAlignment(Pos.CENTER);
        buttonsGrid.setPadding(new Insets(450, 50, 80, 100));
        buttonsGrid.setHgap(20);
        buttonsGrid.setVgap(10);

        buttonsGrid.add(btnContinueGame, 1, 0);
        buttonsGrid.add(btnNewGame, 0, 0);
        buttonsGrid.add(btnSaveGame, 0, 1);
        buttonsGrid.add(btnSaveCloseGame, 1, 1);
        buttonsGrid.add(btnCloseGame, 1, 2);


        return root;
    }

    private Pawn makePawn(PawnType pawnType, int col, int row, int pieceNumber) {

        Pawn pawn = new Pawn(pawnType, col, row, pieceNumber);
        /*
         * the last stage of moving the pawn, previous stages are included in Pawn class
         */

        pawn.setOnMouseReleased(event -> {

            int oldCol = pawn.getCol();
            int oldRow = pawn.getRow();
            int newCol = (int) ((pawn.getLayoutX() + (TILE_SIZE / 2)) / TILE_SIZE);
            int newRow = (int) ((pawn.getLayoutY() + (TILE_SIZE / 2)) / TILE_SIZE);
            //System.out.println("newCOl " + newCol + ", newRow: " + newRow);
            MoveType result = Controller.INSTANCE.checkMove(pawn, newCol, newRow);
            //System.out.println("move type: " + result);
            if (result == MoveType.FORBIDDEN) {
                pawn.relocate(oldCol * TILE_SIZE, oldRow * TILE_SIZE);
                //System.out.println("Forbitten move...");

            } else if (result == MoveType.NORMAL) {

                relocateToNewField(pawn, newCol, newRow, table[oldCol][oldRow]);
                whoMove.setText(doesMovementSummary(pawn, false));
//                System.out.println("table new: " + table[newCol][newRow].toString() + "table old: "
//                        + table[oldCol][oldRow].toString());

            } else if (result == MoveType.KILLING) {

                relocateToNewField(pawn, newCol, newRow, table[oldCol][oldRow]);
                //checkBeatingForGivenPawn(pawn);
                int neighborCol = (newCol + oldCol) / 2;
                int neighborRow = (newRow + oldRow) / 2;
                //System.out.println("neibCol " + neighborCol + " neibRow " + neighborRow);
                //System.out.println(table[neighborCol][neighborRow].toString());
                Pawn tempPawn = table[neighborCol][neighborRow].getPawn();
                int numb = tempPawn.getPawnNumber();
                //System.out.println("tempPawn: " + tempPawn);
                System.out.println("lista pionków w trakcie bicia");
                pawns.getChildren().forEach(System.out::println);
                System.out.println("lista fields w trakcie bicia");
                fields.getChildren().forEach(System.out::println);
                boolean resultOfRemoving = pawns.getChildren().remove(getPawnByNumber(numb));
                //System.out.println("przerwa " + resultOfRemoving);
                //pawns.getChildren().forEach(System.out::println);
                table[neighborCol][neighborRow].setPawn(null);
                System.out.println(table[neighborCol][neighborRow].toString());
                whoMove.setText(doesMovementSummary(pawn, true));
                numbWhitePawns.setText(String.valueOf(userWhite.getNumbOfPawns()));
                numbRedPawns.setText(String.valueOf(userRed.getNumbOfPawns()));
            }
            //to print list of Fields:
            System.out.println("lista pól po wykonaniu ruchu z table");
            getListOfFields();
            System.out.println("lista pól po wykonaniu ruchu z GridPane:");
            fields.getChildren().forEach(System.out::println);
            System.out.println("lista pionków po wykonaniu ruchu z GridPane:");
            pawns.getChildren().forEach(System.out::println);


        });
        return pawn;
    }

    private void relocateToNewField(Pawn pawn, int newCol, int newRow, Field field) {
        pawn.relocate(newCol * TILE_SIZE, newRow * TILE_SIZE);
        pawn.setCol(newCol);
        pawn.setRow(newRow);
        field.setPawn(null);
        table[newCol][newRow].setPawn(pawn);
    }


}
