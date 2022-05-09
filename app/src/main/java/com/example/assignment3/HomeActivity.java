package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.databinding.ActivityMainBinding;

//Version 1.0.1: set home screen for test

public class HomeActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        TextView textview = findViewById(R.id.textView);
        textview.setText("This is HOME activity");


        button = (Button) findViewById(R.id.reportHomeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReportActivity();
            }
        });

    }

    public void openReportActivity(){
        Intent reportIntent = new Intent(this, ReportActivity.class);
        startActivity(reportIntent);
    }
}


