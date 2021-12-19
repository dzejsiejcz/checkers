package com.kodilla.checkers;

import static com.kodilla.checkers.Checkers.userRed;
import static com.kodilla.checkers.Checkers.userWhite;
import static com.kodilla.checkers.PawnType.*;
import static com.kodilla.checkers.Texts.toMove;

public class RightToMove {

    private PawnType type = WHITE;

    public  RightToMove() {
    }

    public PawnType getType() {
        return type;
    }

    public String getUserToMove () {
        if (type == WHITE) {
            return userWhite.getName();
        }
        return userRed.getName();
    }

    public String switchTurn() {
        if (type == WHITE) {
            type = RED;
            System.out.println("Red now");
            return userRed.getName() + toMove;
        }
        type = WHITE;
        System.out.println("White now");
        return userWhite.getName() + toMove;
    }
}
