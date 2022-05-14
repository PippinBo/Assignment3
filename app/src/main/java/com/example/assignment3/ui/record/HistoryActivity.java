package com.example.assignment3.ui.record;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.databinding.RecyclerviewMainBinding;
import com.example.assignment3.entity.User;
import com.example.assignment3.ui.record.adapter.RecyclerViewAdapter;
import com.example.assignment3.ui.record.model.MovementResult;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private List<MovementResult> movementResults;
    private RecyclerViewAdapter adapter;
    private RecyclerviewMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RecyclerviewMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        movementResults = new ArrayList<MovementResult>();
        movementResults = MovementResult.createContactsList();
        adapter = new RecyclerViewAdapter(movementResults);
        //this just creates a line divider between rows
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = binding.etTime.getText().toString().trim();
                String sMovement= binding.etMovement.getText().toString().trim();
                if (!time.isEmpty() || !sMovement.isEmpty()) {
                    int movement =new Integer(sMovement).intValue();
                    saveData(time, movement);
                }



            }
        });
    }
    private void saveData(String time, double movement) {
        MovementResult movementResult = new MovementResult(time, movement);
        movementResults.add(movementResult);
        adapter.addUnits(movementResults);
    }
}
