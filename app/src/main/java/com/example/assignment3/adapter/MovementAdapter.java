package com.example.assignment3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;
import com.example.assignment3.model.MovementTest;

import java.util.List;

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.MyViewHolder>{
    private static List<MovementTest> records;


    public MovementAdapter(List<MovementTest> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public MovementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_record_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovementAdapter.MyViewHolder holder, int position) {
        String date = records.get(position).getRecordDate();
        holder.dateText.setText(date);
        String distance = records.get(position).getRecordDistance();
        holder.distanceText.setText(distance);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView dateText, distanceText;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            dateText = itemView.findViewById(R.id.recordCardDate);
            distanceText = itemView.findViewById(R.id.recordCardDistance);
        }
    }


}
