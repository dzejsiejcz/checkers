package com.kodilla.checkers.model;

import com.kodilla.checkers.utils.PawnType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.kodilla.checkers.utils.Constants.*;

/**
 * pawnNumber to describe pawn on the board
 * type Red or White
 * list fieldsAfterBeats -list of fields where the pawn can move after the possible beating
 */
public class Pawn extends StackPane implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    private int row;
    private int col;
    /**
     * Number of pawn to debug the board.
     */
    private final int pawnNumber;
    private final PawnType type;
    /**
     * List :
     * Arr :
     */
    private List<Coordinates> fieldsAfterBeats = new ArrayList<>();

    private double mouseX, mouseY;

    public Pawn(PawnType type, int col, int row, int pawnNumber) {

        this.type = type;
        this.row = row;
        this.col = col;
        this.pawnNumber = pawnNumber;

        relocate(col * TILE_SIZE, row * TILE_SIZE);

        Circle circle = new Circle(15);
        circle.setFill(type == PawnType.RED ? Color.RED : Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        circle.setTranslateX((TILE_SIZE - 15) / 4);
        circle.setTranslateY((TILE_SIZE - 15) / 4);
        Text text = new Text(String.valueOf(this.pawnNumber));
        text.setTranslateX((TILE_SIZE - 15) / 4);
        text.setTranslateY((TILE_SIZE - 15) / 4);
        getChildren().addAll(circle, text);

        /*
         * moving the pawn on the board, last stage of this movement is implemented in Checkers.makePiece
         */
        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        setOnMouseDragged(event -> relocate((event.getSceneX() - mouseX) + this.col * TILE_SIZE,
                (event.getSceneY() - mouseY) + this.row * TILE_SIZE));

    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public PawnType getType() {
        return type;
    }

    public int getPawnNumber() {
        return pawnNumber;
    }


    public List<Coordinates> getFieldsAfterBeats() {
        return fieldsAfterBeats;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public void setPossiblePositionAfterBeating(int col, int row) {
        fieldsAfterBeats.add(
                Coordinates.Builder.aCoordinates()
                        .colNumber(col)
                        .rowNumber(row)
                        .build());
    }

    public void clearFieldsAfterBeats() {
        fieldsAfterBeats = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Pawn{" +
                ", col=" + col +
                "row=" + row +
                ", type=" + type +
                ", numb=" + pawnNumber +
                ", coordinates=" + fieldsAfterBeats.toString() +
                "}";
    }
}
