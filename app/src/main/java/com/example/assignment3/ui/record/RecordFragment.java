package com.example.assignment3.ui.record;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;
import com.example.assignment3.adapter.MovementAdapter;
import com.example.assignment3.databinding.FragmentRecordBinding;
import com.example.assignment3.entity.Movement;

import java.util.ArrayList;

public class RecordFragment extends Fragment {

    private ArrayList<Movement> recordList;
    private FragmentRecordBinding binding;
    private Button addRecordButtonDialog;
    private RecyclerView recyclerView;
    private MovementAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecordViewModel recordViewModel = new ViewModelProvider(this).get(RecordViewModel.class);

        binding = FragmentRecordBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_record,container,false);

        recyclerView = root.findViewById(R.id.recordRecycle);
        setMovementInfo();
        setAdapter();

        addRecordButtonDialog = root.findViewById(R.id.addRecordButton);
        addRecordButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(root.getContext());
                dialog.setContentView(R.layout.layout_add_record);
                TextView dateTxt = dialog.findViewById(R.id.dateTextView);
                EditText editDistance = dialog.findViewById(R.id.addRecordDistance);
                Button confirmButton = dialog.findViewById(R.id.confirmAddRecord);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        String distance = "";
                        String date = "";
                        distance = editDistance.getText().toString();
                        date = dateTxt.getText().toString();
                        long distanceNum = Long.parseLong(distance);

                        recordList.add(0, new Movement(111,"21/10/2022", distanceNum));
                        adapter.notifyItemInserted(0);
                        recyclerView.scrollToPosition(0);

                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        //final TextView textView = binding.textRecord;
        //recordViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    private void setAdapter() {
        adapter = new MovementAdapter(recordList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setMovementInfo() {
        recordList = new ArrayList<Movement>();
        recordList.add(new Movement(111,"20/12/2022",100));
        recordList.add(new Movement(111,"19/12/2022",200));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
