package com.kodilla.checkers.logic;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static com.kodilla.checkers.Checkers.*;
import static com.kodilla.checkers.logic.Controller.getListOfRowsOfOneTypeOfPawns;
import static com.kodilla.checkers.model.Texts.*;
import static com.kodilla.checkers.utils.PawnType.RED;
import static com.kodilla.checkers.utils.PawnType.WHITE;

public class StateOfGame implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    private boolean isGame = true;
    int minRowOfReds = 7;
    int maxRowOfWhites = 0;
    String winner = null;

    public StateOfGame() {
    }

    public String whoWon() {
        int whitePawns = userWhite.getNumbOfPawns();
        int redPawns = userRed.getNumbOfPawns();

        if (whitePawns == 0) {
            isGame = false;
            winner = userRed.getName() + won;
            return winner;
        }
        if (redPawns == 0) {
            isGame = false;
            winner = userWhite.getName() + won;
            return winner;
        }

        List<Integer> reds = getListOfRowsOfOneTypeOfPawns(RED);
        List<Integer> whites = getListOfRowsOfOneTypeOfPawns(WHITE);
        minRowOfReds = reds.stream().sorted().toList().get(0);
        maxRowOfWhites = whites.stream().sorted().toList().get(whites.size() - 1);
        //System.out.println("min row reds: " + minRowOfReds);
        //System.out.println("max row whites: " + maxRowOfWhites);

        if (maxRowOfWhites < minRowOfReds - 1) {
            isGame = false;
            if (whitePawns > redPawns) {
                winner = userWhite.getName() + won;
            } else if (whitePawns < redPawns) {
                winner = userRed.getName() + won;
            } else {
                winner = draw;
            }
        }

        return winner;
    }

    public boolean isGame() {
        return isGame;
    }

    public void cleanStateOfGame() {
        isGame = true;
        winner = null;
    }

    @Override
    public String toString() {
        return "StateOfGame{" +
                "isGame=" + isGame +
                ", minRowOfReds=" + minRowOfReds +
                ", maxRowOfWhites=" + maxRowOfWhites +
                '}';
    }
}
