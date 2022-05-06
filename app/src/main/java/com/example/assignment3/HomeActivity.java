package com.example.assignment3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Version 1.0.1: set home screen for test

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        TextView textview = findViewById(R.id.textView);
        textview.setText("This is HOME activity");
    }
}


