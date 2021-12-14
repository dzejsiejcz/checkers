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

        if (newCol<0 || newRow<0 || newCol>=WIDTH || newRow>=HEIGHT){
            return FORBIDDEN;
        }
        Field newField = Checkers.table[newCol][newRow];
        if (newField.getColor().equals(COLOR_WHITE) || newField.hasPiece()){
            return FORBIDDEN;
        }
        int deltaY = newRow - piece.getRow();
        if (piece.getType() == PieceType.WHITE && deltaY>0) {
            return FORBIDDEN;
        }
        if (piece.getType() == PieceType.RED && deltaY<0) {
            return FORBIDDEN;
        }
        int deltaX = newCol - piece.getCol();
        /**
         *
         *
         */
        if (abs(deltaX) == 1 && abs(deltaY) == 1 && !piece.isBeating()) {
            return NORMAL;
        }
        if (abs(deltaX) == 2 && abs(deltaY) == 2 && piece.isBeating()) {
            return NORMAL; //zmienić na KILLING
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
