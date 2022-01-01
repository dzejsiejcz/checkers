package com.kodilla.checkers.model;

import javafx.scene.control.Button;

import java.io.Serial;
import java.io.Serializable;

import static com.kodilla.checkers.utils.Constants.TILE_SIZE;

public class Field extends Button implements Serializable {

    @Serial
    private static final long serialVersionUID = 6L;

    private final int row;
    private final int col;
    private final String color;
    private Pawn pawn;

    public Field(int col, int row, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
        setMinSize(TILE_SIZE, TILE_SIZE);
        setStyle(color);
        relocate(col * TILE_SIZE, row * TILE_SIZE);

        setOnMouseClicked(event -> System.out.println("col: " + col + "row: " + row));
    }

    public Field(int col, int row, String color, Pawn pawn) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.pawn = pawn;
        setMinSize(TILE_SIZE, TILE_SIZE);
        setStyle(color);
        relocate(col * TILE_SIZE, row * TILE_SIZE);

        setOnMouseClicked(event -> System.out.println("col: " + col + "row: " + row));
    }


    public boolean hasPawn() {
        return pawn != null;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public String getColor() {
        return color;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Field{" +
                ", col=" + col +
                "row=" + row +
                "color=" + color +
                ", pawn=" + pawn +
                '}';
    }
}
