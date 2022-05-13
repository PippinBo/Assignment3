package com.example.assignment3.ui.record;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.databinding.RecyclerviewMainBinding;
import com.example.assignment3.ui.record.adapter.RecyclerViewAdapter;
import com.example.assignment3.ui.record.model.MovementResult;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private List<MovementResult> units;
    private RecyclerViewAdapter adapter;
    private RecyclerviewMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RecyclerviewMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        units=new ArrayList<MovementResult>();
        units = MovementResult.createContactsList();
        adapter = new RecyclerViewAdapter(units);
        //this just creates a line divider between rows
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unit = binding.etUnit.getText().toString().trim();
                String smark= binding.etMark.getText().toString().trim();
                if (!unit.isEmpty() || !smark.isEmpty()) {
                    int mark=new Integer(smark).intValue();
                    saveData(unit, mark);
                }
            }
        });
    }
    private void saveData(String unit, int mark) {
        MovementResult movementResult = new MovementResult(unit, mark);
        units.add(movementResult);
        adapter.addUnits(units);
    }
}
