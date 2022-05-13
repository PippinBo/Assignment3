package com.example.assignment3.ui.record;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

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
import com.example.assignment3.entity.User;
import com.example.assignment3.viewmodel.UserViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class RecordFragment extends Fragment {

    private ArrayList<Movement> recordList;
    private FragmentRecordBinding binding;
    private Button addRecordButtonDialog;
    private RecyclerView recyclerView;
    private MovementAdapter adapter;
    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecordViewModel recordViewModel = new ViewModelProvider(this).get(RecordViewModel.class);

        binding = FragmentRecordBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_record,container,false);

        //
        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(UserViewModel.class);

        // Recycler View
        setMovementInfo();
        recyclerView = root.findViewById(R.id.recordRecycle);
        setAdapter();

        // Add Record
        addRecordButtonDialog = root.findViewById(R.id.addRecordButton);
        addRecordButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(root.getContext());
                dialog.setContentView(R.layout.layout_add_record);
                TextView dateTxt = dialog.findViewById(R.id.dateTextView);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                dateTxt.setText(dateFormat.format(date));
                EditText editDistance = dialog.findViewById(R.id.addRecordDistance);
                Button confirmButton = dialog.findViewById(R.id.confirmAddRecord);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        String distance = "";
                        String todayDate = "";
                        distance = editDistance.getText().toString();
                        todayDate = dateTxt.getText().toString();
                        long distanceNum = Long.parseLong(distance);

                        Bundle bundle = getActivity().getIntent().getExtras();
                        User user = bundle.getParcelable("loginUser");


                        Movement movement = new Movement(user.getUid(),todayDate,distanceNum);
                        userViewModel.insertMovement(movement);

                        recordList.add(0, new Movement(user.getUid(), todayDate, distanceNum));
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
