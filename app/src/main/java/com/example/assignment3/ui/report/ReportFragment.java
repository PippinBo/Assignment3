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
import com.example.assignment3.databinding.FragmentReportBinding;

// this fragment is for report page, feel free to edit

public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReportViewModel reportViewModel =
                new ViewModelProvider(this).get(ReportViewModel.class);


        assert container != null;
        container.clearDisappearingChildren();
        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Button ID = (Button) root.findViewById(R.id.generate_report);
        Button ID2 = (Button) root.findViewById(R.id.generate_pieChart);
        ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                GenerateReportFragment GenerateReport = new GenerateReportFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, GenerateReport);
                fragmentTransaction.commit();
            }
        });
        ID2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new PieChartFragment());
                fragmentTransaction.commit();
            }
        });




        final TextView textView = binding.textSlideshow;
        reportViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}