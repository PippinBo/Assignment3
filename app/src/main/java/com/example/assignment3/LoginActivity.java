package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3.databinding.LoginActivityBinding;
import com.example.assignment3.entity.Movement;
import com.example.assignment3.entity.User;
import com.example.assignment3.entity.relationship.UserWithMovements;
import com.example.assignment3.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

//Version 1.0.2: set up --- Lichen

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private LoginActivityBinding binding;
    private UserViewModel userViewModel;
    // Idea from Tut9
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // Firebase
        auth = FirebaseAuth.getInstance();
        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);
        // Sign up
        binding.signupButton.setOnClickListener(v -> {
            // dialog!
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Confirm");
            builder.setMessage("Ready for Sign up?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                dialog.dismiss();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
        // Reset Password
        binding.forgetButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ResetActivity.class)));
        // Sign in
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
                userViewModel.findByEmail(txt_email).observe(this, user -> {
                    String msg;
                    if (user != null) {
                        // insert test gyms
                        gymForTest();

                        msg = "Hello " + user.getName();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("loginUser", user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else {
                        msg = "Login in Fail, no user in your Local database";
                    }
                    toastMsg(msg);
                });
            } else {
                String msg = "Login in Fail, please check your email and password";
                toastMsg(msg);
            }
        });
    }

    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    // Just for test
    public void gymForTest(){
        userViewModel.findByEmail("TestGym1@gmail.com").observe(this, user -> {
            if (user == null) {
                User user1 = new User("TestGym1@gmail.com", "Password1!", "Gym", "Test Gym 1", "Campus Centre, Clayton, VIC 3168");
                User user2 = new User("TestGym2@gmail.com", "Password2!", "Gym", "Test Gym 2", "Balwyn Park, Balwyn, VIC 3103");
                User user3 = new User("TestUser1@gmail.com", "Password1!", "User", "Test User 1", "Treyvaud Memorial Park, 32A Chadstone Rd, VIC 3145");
                userViewModel.insertUser(user1);
                userViewModel.insertUser(user2);
                userViewModel.insertUser(user3);
                Movement move1 = new Movement(3, "2022/05/03", 600);
                Movement move2 = new Movement(3, "2022/05/13", 700);
                userViewModel.insertMovement(move1);
                userViewModel.insertMovement(move2);
            }
        });
        //
        // Hi benson, here is the sample code for changing all movements' times attribute for one User
        //
        //userViewModel.getMovementByEmail( USER EMAIL HERE).observe(this, new Observer<List<UserWithMovements>>() {
        //    @Override
        //    public void onChanged(List<UserWithMovements> userWithMovements) {
        //        for (UserWithMovements temp : userWithMovements){
        //            for (Movement m : temp.movements){
        //                m.setTime("11111");
        //                userViewModel.updateMovement(m);
        //            }
        //        }
        //    }
        //});
    }
}