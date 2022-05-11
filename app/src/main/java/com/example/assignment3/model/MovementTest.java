package com.example.assignment3.model;

import com.example.assignment3.adapter.MovementAdapter;

import java.util.ArrayList;
import java.util.List;


public class MovementTest {
    private String recordDate;
    private String recordDistance;

    public MovementTest(String date, String distance)
    {
        recordDate = date;
        recordDistance = distance;
    }

    public String getRecordDate(){
        return recordDate;
    }

    public String getRecordDistance(){
        return recordDistance;
    }

}
