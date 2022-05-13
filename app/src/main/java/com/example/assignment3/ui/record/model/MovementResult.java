package com.example.assignment3.ui.record.model;

import java.util.ArrayList;
import java.util.List;

public class MovementResult {
    private String mtime;
    private double mmovement;
    public MovementResult(String time, double movement) {
        mtime = time;
        mmovement = movement;
    }
    public String getTime() { return mtime; }
    public double getMovement() {
        return mmovement;
    }
    //this is used to populate the list with a few items at the start of the application
//it is static so it can be called without instantiating the class
    public static List<MovementResult> createContactsList() {
        List<MovementResult> movementResults = new ArrayList<MovementResult>();
        movementResults.add(new MovementResult( "100s",123.54));
        movementResults.add(new MovementResult( "90s", 123.01));
        return movementResults;
    }
}
