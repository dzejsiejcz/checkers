package com.kodilla.checkers;

public class User {

    private String name;
    private boolean isBeating;
    private int numbPieces = 12;

    public User(String name, boolean isBeating) {
        this.name = name;
        this.isBeating = isBeating;
    }

    public String getName() {
        return name;
    }

    public boolean isBeating() {
        return isBeating;
    }

    public int getNumbPieces() {
        return numbPieces;
    }

    public void setBeating(boolean beating) {
        isBeating = beating;
    }

    public void setNumbPieces(int numbPieces) {
        this.numbPieces = numbPieces;
    }
}
