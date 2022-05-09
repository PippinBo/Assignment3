package com.example.assignment3;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.databinding.SignupBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Version 1.0.2: set up --- Lichen
//Note: user1: lichen100@gmail.com  Qq1234567! <Gym>
//      user2: lichen101@gmail.com  Qq1234567! <User>
//      user3: lichen102@gmail.com  Qq1234567! <User>
//      user4: lichen103@gmail.com  Qq1234567! <Gym>
//      user5: test1@gmail.com      Password1! <User>
//      user6: test2@gmail.com      Password2! <User>

public class SignupActivity extends AppCompatActivity {
    private SignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.continueButton.setOnClickListener(v -> {
            //Get Text
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
                addUser(email_txt, password_txt_1);
        });
    }

    private void addUser(String email_txt, String password_txt_1) {
        // create a User object and pass it to next view
        Intent intent = new Intent(SignupActivity.this, DataEntryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", new User(email_txt, password_txt_1, "", "", ""));
        intent.putExtras(bundle);
        startActivity(intent);
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
