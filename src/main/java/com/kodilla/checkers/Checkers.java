package com.kodilla.checkers;

import static com.kodilla.checkers.logic.Controller.doesMovementSummary;
import static com.kodilla.checkers.utils.Constants.COLOR_BLUE;
import static com.kodilla.checkers.utils.Constants.COLOR_WHITE;
import static com.kodilla.checkers.utils.Constants.HEIGHT;
import static com.kodilla.checkers.utils.Constants.PADDING_X;
import static com.kodilla.checkers.utils.Constants.PADDING_Y;
import static com.kodilla.checkers.utils.Constants.TILE_SIZE;
import static com.kodilla.checkers.utils.Constants.WIDTH;
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
import com.sun.javafx.menu.MenuItemBase;
import com.sun.javafx.stage.EmbeddedWindow;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
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
import javafx.stage.Stage;

public class Checkers extends Application {

    public static User userRed = new User(Texts.reds, RED, false);
    public static User userWhite = new User(Texts.whites, WHITE, false);
    public static RightToMove rightToMove = new RightToMove();
    public static Field[][] table = new Field[WIDTH][HEIGHT];
    public static StateOfGame game = new StateOfGame();

    private static final String CHECKERS = "Checkers";
    private final Image board = new Image("file:src/main/resources/table.png");
    private final Group fields = new Group();
    private final Group pawns = new Group();
    private final GridPane grid = new GridPane();
    private final GridPane buttonsGrid = new GridPane();
    private final Label inviting = new Label(Texts.nameOfTheGame);
    private final Label redName = new Label(Texts.reds);
    private final Label whiteName = new Label(Texts.whites);
    private final Label whoMove = new Label(rightToMove.getUserToMove() + " have to move");
    private final Label numbRedPawnsDescribing = new Label(Texts.numbPawns);
    private final Label numbWhitePawnsDescribing = new Label(Texts.numbPawns);
    private final Label numbRedPawns = new Label(String.valueOf(userRed.getNumbOfPawns()));
    private final Label numbWhitePawns = new Label(String.valueOf(userWhite.getNumbOfPawns()));
    private int pawnNumber = 0;
    private Pane root = new Pane();
    private Button btnNewGame = new Button("New Game");

    private final Font arial40 = new Font("Arial", 40);
    private final Font arial30 = new Font("Arial", 30);
    private final Font arial20 = new Font("Arial", 20);



    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createBoard());
        primaryStage.setTitle(CHECKERS);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        btnNewGame.setOnAction(e -> {
            primaryStage.close();
            cleanup();
            Scene scene2 = new Scene(createBoard());
            primaryStage.setTitle(CHECKERS);
            primaryStage.setScene(scene2);
            primaryStage.setResizable(false);
            primaryStage.show();

        });

    }

    void cleanup() {
        grid.getChildren().removeAll();
        fields.getChildren().removeAll();
        pawns.getChildren().removeAll();
        buttonsGrid.getChildren().removeAll();
        root.getChildren().removeAll();
    }

    private Parent createBoard() {
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
                if (row == 2 && ((col + row) % 2) != 0) {
                    pawn = makePawn(RED, col, row, pawnNumber);
                    pawnNumber++;
                }
                if (row ==5 && ((col + row) % 2) != 0) {
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
        buttonsGrid.setPadding(new Insets(500, 50, 80, 100));
        buttonsGrid.setHgap(20);
        buttonsGrid.setVgap(10);

        buttonsGrid.add(btnNewGame, 0, 0);


        return root;
    }

    private Pawn makePawn(PawnType pawnType, int col, int row, int pieceNumber) {

        Pawn pawn = new Pawn(pawnType, col, row, pieceNumber);
        /*
         * the last stage of moving the pawn, previous stages are included in Pawn class
         */
        //while (game.isGame()) {
        pawn.setOnMouseReleased(event -> {

            int oldCol = pawn.getCol();
            int oldRow = pawn.getRow();
            int newCol = (int) ((pawn.getLayoutX() + (TILE_SIZE / 2)) / TILE_SIZE);
            int newRow = (int) ((pawn.getLayoutY() + (TILE_SIZE / 2)) / TILE_SIZE);
            System.out.println("newCOl " + newCol + ", newRow: " + newRow);
            MoveType result = Controller.INSTANCE.checkMove(pawn, newCol, newRow);

            if (result == MoveType.FORBIDDEN) {
                pawn.relocate(oldCol * TILE_SIZE, oldRow * TILE_SIZE);
                System.out.println("Forbitten move...");

            } else if (result == MoveType.NORMAL) {

                relocateToNewField(pawn, newCol, newRow, table[oldCol][oldRow]);
                whoMove.setText(doesMovementSummary(pawn, false));
                System.out.println("table new: " + table[newCol][newRow].toString() + "table old: "
                        + table[oldCol][oldRow].toString());

            } else if (result == MoveType.KILLING) {
                relocateToNewField(pawn, newCol, newRow, table[oldCol][oldRow]);
                int neighborCol = (newCol + oldCol) / 2;
                int neighborRow = (newRow + oldRow) / 2;
                System.out.println("neibCol " + neighborCol + " neibRow " + neighborRow);

                pawns.getChildren().remove(table[neighborCol][neighborRow].getPawn());
                table[neighborCol][neighborRow].setPawn(null);
                whoMove.setText(doesMovementSummary(pawn, true));
                numbWhitePawns.setText(String.valueOf(userWhite.getNumbOfPawns()));
                numbRedPawns.setText(String.valueOf(userRed.getNumbOfPawns()));
            }

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

    public static void main(String[] args) {
        launch(args);
    }

}
