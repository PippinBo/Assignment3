package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

//Version 1.0.2: set up --- Lichen

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ActivityMainBinding binding;
    // Most from Tut9
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        binding.signupButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SignupActivity.class))
        );
        binding.signinButton.setOnClickListener(v -> {
            String txt_Email = binding.emailEditText.getText().toString();
            String txt_Pwd = binding.passwordEditText.getText().toString();
            loginUser(txt_Email,txt_Pwd);
        });
    }

    private void loginUser(String txt_email, String txt_pwd) {

        if (txt_email.isEmpty() || txt_pwd.isEmpty()) {
            Toast.makeText(this, "Please make sure to fill in your email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(txt_email, txt_pwd).addOnSuccessListener(authResult -> {
            String msg = "Login Successful";
            toastMsg(msg);
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        });
    }
    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}