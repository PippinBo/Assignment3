package com.example.assignment3.ui.record;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;
import com.example.assignment3.adapter.MovementAdapter;
import com.example.assignment3.databinding.FragmentRecordBinding;
import com.example.assignment3.entity.Movement;
import com.example.assignment3.entity.User;
import com.example.assignment3.entity.relationship.UserWithMovements;
import com.example.assignment3.repository.UserRepository;
import com.example.assignment3.viewmodel.UserViewModel;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecordFragment extends Fragment {

    private ArrayList<Movement> recordList;
    private FragmentRecordBinding binding;
    private Button addRecordButtonDialog;
    private RecyclerView recyclerView;
    private MovementAdapter adapter;
    private UserViewModel userViewModel;
    private UserRepository userRepository;
    private View root;
    private User user;
    private Context context = getActivity();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecordViewModel recordViewModel = new ViewModelProvider(this).get(RecordViewModel.class);

        binding = FragmentRecordBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_record,container,false);

        //
        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(UserViewModel.class);

        // Recycler View
        setMovementInfo();
        recyclerView = root.findViewById(R.id.recordRecycle);
        setAdapter();

        // User
        Bundle bundle = getActivity().getIntent().getExtras();
        User user = bundle.getParcelable("loginUser");


        // Add Record
        addRecordButtonDialog = root.findViewById(R.id.addRecordButton);
        addRecordButtonDialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();

                //CompletableFuture<Movement> test = userViewModel.checkDailyEntry(user.getUid(),dateFormat.format(date));
                //if (test != null){
                  //  Toast.makeText(getActivity(), "Already recorded today!", Toast.LENGTH_SHORT).show();
                    //return;
                //}


                // Return error if so

                Dialog dialog = new Dialog(root.getContext());
                dialog.setContentView(R.layout.layout_add_record);
                TextView dateTxt = dialog.findViewById(R.id.dateTextView);

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

                        // Database
                        Movement movement = new Movement(user.getUid(),todayDate,distanceNum);
                        userViewModel.insertMovement(movement);

                        // Recycler
                        recordList.add(0, new Movement(user.getUid(), todayDate, distanceNum));
                        adapter.notifyItemInserted(recordList.size()-1);
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
        Bundle bundle = getActivity().getIntent().getExtras();
        user = bundle.getParcelable("loginUser");
        adapter = new MovementAdapter(recordList, userViewModel,user);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setMovementInfo() {
        recordList = new ArrayList<Movement>();

        // Database
        Bundle bundle = getActivity().getIntent().getExtras();
        User user = bundle.getParcelable("loginUser");
        LiveData<List<Movement>> movementList = userViewModel.getMovementById(user.getUid());

        userViewModel.getMovementByEmail(user.getEmail()).observe(getActivity(), new Observer<List<UserWithMovements>>() {
            @Override
            public void onChanged(List<UserWithMovements> userWithMovements) {
                recordList = new ArrayList<Movement>();
                //Toast.makeText(getActivity(),"yo",Toast.LENGTH_SHORT).show();
                for (UserWithMovements temp : userWithMovements){
                    for (Movement temp2 : temp.movements){
                        Movement test1 = new Movement(temp2.getUserId(),temp2.getTime(),temp2.getMovement());
                        recordList.add(test1);
                        Collections.reverse(recordList);
                    }

            }
                setAdapter();
                recyclerView.getAdapter().notifyDataSetChanged();

        }});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
