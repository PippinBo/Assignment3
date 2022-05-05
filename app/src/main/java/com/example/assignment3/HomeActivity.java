package com.example.assignment3;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.databinding.HomeActivityBinding;

//Version 1.0.1: set home screen for test

public class HomeActivity extends AppCompatActivity {
    private HomeActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        
        //binding.welcomeTextView.setText("This is HOME activity");
    }
}

