package com.kodilla.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.checkers.Checkers.*;


public class Piece extends StackPane {

    private int row;
    private int col;
    int description;
    private final PieceType type;
    private boolean isBeating = false;
    private List<Integer[]> fieldsAfterBeats = new ArrayList<>();

    private double mouseX, mouseY;




    public Piece( PieceType type, int col, int row, int description) {

        this.type = type;
        this.row = row;
        this.col = col;
        this.description = description;

        relocate(col * TILE_SIZE, row * TILE_SIZE);

        Circle circle = new Circle(15);
        circle.setFill(type == PieceType.RED ? Color.RED : Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        circle.setTranslateX((TILE_SIZE - 15)/4);
        circle.setTranslateY((TILE_SIZE - 15)/4);
        Text text = new Text (String.valueOf(description));
        text.setTranslateX((TILE_SIZE - 15)/4);
        text.setTranslateY((TILE_SIZE - 15)/4);
        getChildren().addAll(circle, text);

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        setOnMouseDragged(event -> relocate((event.getSceneX()-mouseX)+this.col*TILE_SIZE,
                (event.getSceneY()-mouseY)+this.row*TILE_SIZE));

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public PieceType getType() {
        return type;
    }

    public int getDescription() {
        return description;
    }

    public boolean isBeating() {
        return isBeating;
    }

    public List<Integer[]> getFieldsAfterBeats() {
        return fieldsAfterBeats;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setBeating(boolean beating) {
        isBeating = beating;
    }

    public void setAvailableBeat(int col, int row) {
        fieldsAfterBeats.add(new Integer[]{col, row});
    }

    @Override
    public String toString() {
        return "Piece{" +
                "row=" + row +
                ", col=" + col +
                ", type=" + type +
                ", isBeating=" + isBeating +
                '}';
    }
}
