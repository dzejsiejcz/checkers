package com.kodilla.checkers;


import java.util.ArrayList;
import java.util.List;

import static com.kodilla.checkers.Checkers.*;
import static com.kodilla.checkers.MoveType.*;
import static com.kodilla.checkers.PieceType.RED;
import static com.kodilla.checkers.PieceType.WHITE;
import static java.lang.Math.abs;


public class Controller {

    public static  Controller INSTANCE = new Controller();

    private Controller(){}

    public MoveType checkMove(Piece piece, int newCol, int newRow) {
        int oldCol = piece.getCol();
        int oldRow = piece.getRow();

        //whose turn?
        if (piece.getType() == RED) {
            if (userRed.notYourTurn()) {
                return FORBIDDEN;
            }
        }
        if (piece.getType() == WHITE) {
            if (userWhite.notYourTurn()) {
                return FORBIDDEN;
            }
        }

        // don't move out of board
        if (newCol<0 || newRow<0 || newCol>=WIDTH || newRow>=HEIGHT){
            return FORBIDDEN;
        }
        // don't move to white field or occupied field
        Field newField = table[newCol][newRow];
        if (newField.getColor().equals(COLOR_WHITE) || newField.hasPiece()){
            return FORBIDDEN;
        }

        //check possibility of beating, if possible, user must beat
        Integer[] checkingFieldAfterBeat = new Integer[]{newCol, newRow};
        System.out.println("bije?: " + piece.isBeating());
        if (piece.isBeating()) {
            for (Integer[] currentField: piece.getFieldsAfterBeats()) {
                if (currentField[0].equals(checkingFieldAfterBeat[0]) && currentField[1].equals(checkingFieldAfterBeat[1])) {
                    return KILLING;
                }
            }
            return FORBIDDEN;
        }

        //check right direction of move, except beating
        int deltaY = newRow - oldRow;
        if (piece.getType() == WHITE && deltaY>0 && !piece.isBeating()) {
                return FORBIDDEN;
            }

        if (piece.getType() == RED && deltaY<0 && !piece.isBeating()) {
            return FORBIDDEN;
        }

        int deltaX = newCol - oldCol;

        // normal movement if no beating, beating is obligatory
        if (abs(deltaX) == 1 && abs(deltaY) == 1 && !piece.isBeating() && !checkBeating(piece.getType())) {
            System.out.println("1sze sprawdzenie");
            return NORMAL;
        }

        return FORBIDDEN;
    }

    //method to check if in the board is available beating for given PieceType and set this info to each field
    public static boolean checkBeating (PieceType color) {
        Field checkingField;
        Field neighbor;
        Field fieldAfterBeating;
        boolean response = false;
        boolean isBeating = false;
        for (int x=0; x<WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                checkingField = table[x][y];
                if (checkingField.hasPiece()) {
                    Piece checkingPiece = checkingField.getPiece();
                    PieceType pieceType = checkingPiece.getType();
                    if (pieceType == color) {
                        checkingPiece.setBeating(false);
                        for (int i = -1; i < 2; i = i + 2) {
                            for (int j = -1; j < 2; j = j + 2) {
                                int neighborCol = x + i;
                                int neighborRow = y + j;
                                int fieldAfterBeatCol = neighborCol + i;
                                int fieldAfterBeatRow = neighborRow + j;
                                if (fieldAfterBeatCol >= 0 && fieldAfterBeatRow >= 0 &&
                                        fieldAfterBeatRow < HEIGHT && fieldAfterBeatCol < WIDTH) {
                                    neighbor = table[neighborCol][neighborRow];
                                    fieldAfterBeating = table[fieldAfterBeatCol][fieldAfterBeatRow];
                                    if (neighbor.hasPiece() && !fieldAfterBeating.hasPiece()) {
                                        PieceType neighborType = neighbor.getPiece().getType();
                                        if (neighborType != pieceType) {
                                            checkingPiece.setBeating(true);
                                            checkingPiece.setAvailableBeat(fieldAfterBeatCol, fieldAfterBeatRow);
                                            System.out.println("do zbicia: " + neighbor.getPiece().getDescription());
                                            response = true;
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println(pieceType + " " + checkingPiece.getDescription()
                                + " ma bicie? " + checkingPiece.isBeating());

                    }
                }
            }
        }
        System.out.println("Czy jakikolwiek pion ma bicie? " + response);
        return response;
    }

    public static String switchTurn(Piece piece, boolean killed) {
        checkBeating(RED);
        checkBeating(WHITE);
        if (piece.getType() == RED) {
            userRed.setYourTurn(false);
            userWhite.setYourTurn(true);
            if (killed) {
                userRed.subtractOnePiece();
            }
            System.out.println("White now");
            return userWhite.getName();
        }
        if (piece.getType() == WHITE) {
            userRed.setYourTurn(true);
            userWhite.setYourTurn(false);
            if (killed) {
                userWhite.subtractOnePiece();
            }
            System.out.println("Red now");
            return userRed.getName();
        }

        return "Unknown";
    }







}
