package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3.databinding.LoginActivityBinding;
import com.example.assignment3.entity.User;
import com.example.assignment3.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;

//Version 1.0.2: set up --- Lichen

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private LoginActivityBinding binding;
    private UserViewModel userViewModel;
    // Most from Tut9
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);

        binding.signupButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class))
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

        auth.signInWithEmailAndPassword(txt_email, txt_pwd).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                String msg = "Login Successful";
                toastMsg(msg);

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                String msg = "Login in Fail, please check your email and password";
                toastMsg(msg);
            }
        });
    }

    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}