package com.kodilla.checkers;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.kodilla.checkers.Checkers.*;
import static java.lang.Math.round;

public class Controller {

    public static  Controller INSTANCE = new Controller();
    private final List<Field> fields = new ArrayList<>();


    private Controller(){}

    public void addField(Field field) {
        this.fields.add(field);
    }

//    public void click(Field field) {
//        int col = field.getCol();
//        int row = field.getRow();
//        System.out.println("Field clicked col= " + col + ", row= " + row);
//        field.setStyle("-fx-background-color : #008000;");
//
//        fields.stream()
//                .filter(f -> f.getCol() == col+1 && f.getRow() == row)
//                .forEach(f -> f.setStyle("-fx-background-color : #000000;"));
//    }





}
