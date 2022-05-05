package com.example.assignment3;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.databinding.DataEntryBinding;
import com.google.firebase.auth.FirebaseAuth;

//Version 1.0.2: set up --- Lichen
public class DataEntryActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DataEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataEntryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_txt = binding.nameEditText.getText().toString();
                String address_txt_1 = binding.streetEditText.getText().toString();
                String address_txt_2 = binding.suburbEditText.getText().toString();
                String address_txt_3 = binding.stateEditText.getText().toString();
                //validation

            }
        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.nameEditText.setText("");
                binding.streetEditText.setText("");
                binding.suburbEditText.setText("");
                binding.stateEditText.setText("");
                String msg = "Clear All";
                toastMsg(msg);
            }
        });
    }

    public void toastMsg(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
