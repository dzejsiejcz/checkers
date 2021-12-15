package com.kodilla.checkers;


import java.util.ArrayList;
import java.util.List;

import static com.kodilla.checkers.Checkers.*;
import static com.kodilla.checkers.MoveType.*;
import static java.lang.Math.abs;


public class Controller {

    public static  Controller INSTANCE = new Controller();
    private final List<Field> fields = new ArrayList<>();



    private Controller(){}

    public void addField(Field field) {
        this.fields.add(field);
    }

    public MoveType checkMove(Piece piece, int newCol, int newRow) {
        int oldCol = piece.getCol();
        int oldRow = piece.getRow();

        if (newCol<0 || newRow<0 || newCol>=WIDTH || newRow>=HEIGHT){
            return FORBIDDEN;
        }
        Field newField = Checkers.table[newCol][newRow];
        if (newField.getColor().equals(COLOR_WHITE) || newField.hasPiece()){
            return FORBIDDEN;
        }
        int deltaY = newRow - oldRow;
        if (piece.getType() == PieceType.WHITE && deltaY>0) {
            return FORBIDDEN;
        }
        if (piece.getType() == PieceType.RED && deltaY<0) {
            return FORBIDDEN;
        }
        int deltaX = newCol - oldCol;
// dodać warunek isBeating
        if (abs(deltaX) == 1 && abs(deltaY) == 1 && !piece.isBeating()) {
            return NORMAL;
        }

        int neighborCol = (newCol + oldCol) / 2;
        int neighborRow = (newRow + oldRow) / 2;
        Piece neighbor = Checkers.table[neighborCol][neighborRow].getPiece();

        if (neighbor != null && piece.getType() != neighbor.getType()) {
            return KILLING;
        }




        return FORBIDDEN;
    }

    public void checkBeating () {
        Field field;
        for (int x=0; x<WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                field = table[x][y];
                if (field.hasPiece()) {
                    field.getPiece().setBeating(table);
                }
            }
        }
    }







}
