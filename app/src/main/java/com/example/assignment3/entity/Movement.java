package com.example.assignment3.entity;

import androidx.room.Entity;

import java.util.Date;

//Might needed for report
@Entity(tableName = "Movement")
public class Movement {

    public int uid;
    private Date time;
    private long movement;

    public Movement(int uid, Date time, long movement) {
        this.uid = uid;
        this.time = time;
        this.movement = movement;
    }

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

    public Date getTime() { return time; }

    public void setTime(Date time) { this.time = time; }

    public long getMovement() { return movement; }

    public void setMovement(long movement) { this.movement = movement; }
}
