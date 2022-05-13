package com.example.assignment3.ui.record.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.databinding.RecyclerviewLayoutBinding;
import com.example.assignment3.ui.record.model.MovementResult;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter
        <RecyclerViewAdapter.ViewHolder> {

    private static List<MovementResult> movementResults;
    public RecyclerViewAdapter(List<MovementResult> movementResults) {
        this.movementResults = movementResults;
    }
    //creates a new viewholder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        RecyclerviewLayoutBinding binding=
                RecyclerviewLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }
    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int
            position) {
        final MovementResult unit = movementResults.get(position);
        viewHolder.binding.tvRvmark.setText(unit.getTime());
        viewHolder.binding.ivItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementResults.remove(unit);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return movementResults.size();
    }
    public void addUnits(List<MovementResult> results) {
        movementResults = results;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewLayoutBinding binding;
        public ViewHolder(RecyclerviewLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}