package com.example.assignment3.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3.R;
import com.example.assignment3.databinding.FragmentGenerateReportBinding;
import com.example.assignment3.databinding.FragmentReportBinding;

// this fragment is for report page, feel free to edit

public class GenerateReportFragment extends Fragment {

    private FragmentGenerateReportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GenerateReportViewModel generateReportViewModel =
                new ViewModelProvider(this).get(GenerateReportViewModel.class);

        binding = FragmentGenerateReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Button ID = (Button) root.findViewById(R.id.back_button);
        ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ReportFragment Report = new ReportFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, Report);
                fragmentTransaction.commit();

            }
        });


        final TextView textView = binding.generateReport;
        generateReportViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}