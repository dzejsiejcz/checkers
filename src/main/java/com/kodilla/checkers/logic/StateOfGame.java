package com.kodilla.checkers.logic;

import com.kodilla.checkers.model.Pawn;

import java.util.List;

import static com.kodilla.checkers.logic.Controller.getListOfOneTypeOfPawns;
import static com.kodilla.checkers.utils.PawnType.RED;
import static com.kodilla.checkers.utils.PawnType.WHITE;

public class StateOfGame {

    private boolean isGame = true;

    public StateOfGame() {
    }

    public boolean checkEndGame() {

        List<Pawn> reds = getListOfOneTypeOfPawns(RED);
        List<Pawn> whites = getListOfOneTypeOfPawns(WHITE);

        int minRowOfReds = 7;
        int maxRowOfWhites = 0;

        for ( Pawn red : reds ) {
            System.out.println("red row: " + red.getRow() + " " + minRowOfReds);
            if (red.getRow() < minRowOfReds){
                minRowOfReds = red.getRow();
            }
        }

        for ( Pawn white : whites ) {
            System.out.println("white row: " + white.getRow() + " " + maxRowOfWhites);
            if (white.getRow() > maxRowOfWhites) {
                maxRowOfWhites = white.getRow();
            }
        }

        if (maxRowOfWhites < minRowOfReds ) {
            isGame = false;
            return false;
        }

        return true;
    }

    public boolean isGame() {
        return isGame;
    }
}
