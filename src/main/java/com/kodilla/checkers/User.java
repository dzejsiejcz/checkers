package com.kodilla.checkers;

public class User {

    private final String name;
    private boolean isBeating;
    private int numbPieces = 12;
    private boolean yourTurn;
    private final PieceType pieceType;

    public User(String name, PieceType pieceType, boolean isBeating, boolean yourTurn) {
        this.name = name;
        this.isBeating = isBeating;
        this.pieceType = pieceType;
        this.yourTurn = yourTurn;
    }

    public String getName() {
        return name;
    }

    public boolean notYourTurn() {
        return !yourTurn;
    }

    public PieceType getPieceType() {
        return pieceType;
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

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public void subtractOnePiece() {
        numbPieces--;
    }
}
