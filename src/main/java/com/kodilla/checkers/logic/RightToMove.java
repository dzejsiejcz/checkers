package com.kodilla.checkers.logic;

import static com.kodilla.checkers.Checkers.userRed;
import static com.kodilla.checkers.Checkers.userWhite;
import static com.kodilla.checkers.utils.PawnType.*;
import static com.kodilla.checkers.model.Texts.toMove;

import com.kodilla.checkers.utils.PawnType;

import java.io.Serial;
import java.io.Serializable;

public class RightToMove implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private PawnType type = WHITE;

    public RightToMove() {
    }

    public PawnType getType() {
        return type;
    }


    public String getUserToMove() {
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

    public void cleanRightToMove() {
        this.type = WHITE;
    }

    @Override
    public String toString() {
        return "RightToMove{" +
                "type=" + type +
                '}';
    }
}
