package com.kodilla.checkers;

import javafx.scene.control.Button;

import static com.kodilla.checkers.Constants.TILE_SIZE;

public class Field extends Button {
    private int row;
    private int col;
    private String color;
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


    public boolean hasPawn() {
        return pawn != null;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getColor() {
        return color;
    }

    public Pawn getPawn() {
        return pawn;
    }

    @Override
    public String toString() {
        return "Field{" +
                ", col=" + col +
                "row=" + row +
                ", pawn=" + pawn +
                '}';
    }
}
