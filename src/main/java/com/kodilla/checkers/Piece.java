package com.kodilla.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.kodilla.checkers.Checkers.*;


public class Piece extends StackPane {

    private int row;
    private int col;

    private PieceType type;

    private double mouseX, mouseY;


    public Piece( PieceType type, int col, int row) {

        this.type = type;
        this.row = row;
        this.col = col;

        relocate(col * TILE_SIZE, row * TILE_SIZE);

        Circle circle = new Circle(15);
        circle.setFill(type == PieceType.RED ? Color.RED : Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        circle.setTranslateX((TILE_SIZE - 15)/4);
        circle.setTranslateY((TILE_SIZE - 15)/4);
        getChildren().addAll(circle);
        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
        setOnMouseDragged(event -> {
            relocate((event.getSceneX()-mouseX)+this.col*TILE_SIZE,
                    (event.getSceneY()-mouseY)+this.row*TILE_SIZE);
        });

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

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

}
