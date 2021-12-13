package com.kodilla.checkers;

import javafx.scene.control.Button;

import static com.kodilla.checkers.Checkers.*;

public class Field extends Button {
    private int row;
    private int col;
    private String color;
    private Piece piece;


    public Field(int col, int row, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
        setMinSize(TILE_SIZE, TILE_SIZE);
        setStyle(color);
        relocate(col * TILE_SIZE, row * TILE_SIZE);
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
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
}
