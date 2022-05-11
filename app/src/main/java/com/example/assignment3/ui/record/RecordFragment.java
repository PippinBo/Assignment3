package com.example.assignment3.ui.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.assignment3.model.MovementTest;

import java.util.ArrayList;

public class RecordFragment extends Fragment {

    private ArrayList<MovementTest> recordList;
    private FragmentRecordBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { RecordViewModel recordViewModel =
                new ViewModelProvider(this).get(RecordViewModel.class);


        //binding = FragmentRecordBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_record,container,false);

        recyclerView = root.findViewById(R.id.recordRecycle);
        setMovementInfo();
        setAdapter();

        //final TextView textView = binding.textRecord;
        //recordViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void setAdapter() {
        MovementAdapter adapter = new MovementAdapter(recordList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setMovementInfo() {
        recordList = new ArrayList<MovementTest>();
        recordList.add(new MovementTest("20/12/2022","200"));
        recordList.add(new MovementTest("19/12/2022","300"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
