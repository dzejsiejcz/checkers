package com.kodilla.checkers.model;

import com.kodilla.checkers.utils.PawnType;

public class User {

    private final String name;
    private boolean isBeating;
    private int numbOfPawns = 12;

    private final PawnType pawnType;

    public User(String name, PawnType pawnType, boolean isBeating) {
        this.name = name;
        this.isBeating = isBeating;
        this.pawnType = pawnType;
    }

    public String getName() {
        return name;
    }

    public PawnType getPawnType() {
        return pawnType;
    }

    public boolean isBeating() {
        return isBeating;
    }

    public int getNumbPawn() {
        return numbOfPawns;
    }

    public int getNumbOfPawns() {
        return numbOfPawns;
    }

    public void setBeating(boolean beating) {
        isBeating = beating;
    }


    public void subtractOnePawn() {
        numbOfPawns--;
    }
}
