package com.example.assignment3;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.assignment3.databinding.SignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Version 1.0.2: set up --- Lichen
//Note: user1: lichen100@gmail.com  Qq1234567!
//      user2: lichen101@gmail.com  Qq1234567!

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private SignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = binding.emailEditText.getText().toString();
                String password_txt_1 = binding.passwordEditText.getText().toString();
                String password_txt_2 = binding.confirmPasswordEditText.getText().toString();
                //validation
                if (TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt_1) || TextUtils.isEmpty(password_txt_2)) {
                    String msg = "Please enter your information";
                    toastMsg(msg);
                } else if (!isValidPassword(password_txt_1)) {
                    String msg = "Invalid password format";
                    toastMsg(msg);
                } else if (!password_txt_2.equals(password_txt_1)) {
                    String msg = "Please confirm your password";
                    toastMsg(msg);
                } else
                    registerUser(email_txt, password_txt_1);
            }
        });
    }

    private void registerUser(String email_txt, String password_txt_1) {
        auth.createUserWithEmailAndPassword(email_txt,password_txt_1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String msg = "Registration Successful";
                    toastMsg(msg);
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                }else {
                    String msg = "Registration Unsuccessful";
                    toastMsg(msg);
                }
            }
        });
    }

    public void toastMsg(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public boolean isValidPassword(final String password) {
        //validation
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[@#$%^&+=!?])" +  //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
