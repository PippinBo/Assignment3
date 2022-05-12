package com.example.assignment3.ui.report;

import android.content.Intent;
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
import com.example.assignment3.databinding.FragmentBarchartBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

// this fragment is for report page, feel free to edit

public class BarChartFragment extends Fragment {

    private FragmentBarchartBinding binding;
    private BarChart barChart;
    private ArrayList barArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BarChartViewModel barChartViewModel =
                new ViewModelProvider(this).get(BarChartViewModel.class);

        binding = FragmentBarchartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button shareButton = (Button) root.findViewById(R.id.facebook_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Intent intent = new Intent(getActivity(), FacebookActivity.class);
                startActivity(intent);
                fragmentTransaction.commit();
            }
        });


        barChart = root.findViewById(R.id.barReportChart);
        loadPieChartData();


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
        barChartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void loadPieChartData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        getData();
        BarDataSet barDataSet = new BarDataSet(barArrayList, "FitBud");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

    }

    private void getData(){
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry(2f,10));
        barArrayList.add(new BarEntry(3f,20));
        barArrayList.add(new BarEntry(4f,30));
        barArrayList.add(new BarEntry(5f,40));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}