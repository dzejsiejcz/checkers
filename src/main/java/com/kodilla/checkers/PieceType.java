package com.kodilla.checkers;

public enum PieceType {

    WHITE(1), RED(-1);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
