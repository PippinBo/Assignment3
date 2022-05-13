package com.example.assignment3.ui.record.model;

import java.util.ArrayList;
import java.util.List;

public class MovementResult {
    private String time;
    private double movement;
    public MovementResult(String time, double movement) {
        time = time;
        movement = movement;
    }
    public String getTime() { return time; }
    public double getMovement() {
        return movement;
    }
    //this is used to populate the list with a few items at the start of the application
//it is static so it can be called without instantiating the class
    public static List<MovementResult> createContactsList() {
        List<MovementResult> units = new ArrayList<MovementResult>();
        units.add(new MovementResult( "100s",123.54));
        units.add(new MovementResult( "90s", 123.01));
        return units;
    }
}
