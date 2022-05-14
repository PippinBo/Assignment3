package com.example.assignment3.ui.report;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3.BitmapUtil;
import com.example.assignment3.R;
import com.example.assignment3.databinding.FragmentPiechartBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.util.ArrayList;

public class PieChartFragment extends Fragment {
    private Button facebookButton;

    private PieChart pieChart;
    private FragmentPiechartBinding binding;
    private Button id;

    /**
     * 截取全屏
     *
     * @return
     */
    public Bitmap captureScreenWindow() {
        getActivity().getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = getActivity().getWindow().getDecorView().getDrawingCache();
        return bmp;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PieChartViewModel pieChartViewModel =
                new ViewModelProvider(this).get(PieChartViewModel.class);

        binding = FragmentPiechartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        id = (Button) root.findViewById(R.id.back_button);

        facebookButton = (Button) root.findViewById(R.id.facebook_button);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //请求权限
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    ActivityCompat.requestPermissions(getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //调用
                    extracted(facebookButton, id);
                }
//                facebookButton.setVisibility(View.GONE);
//                ID.setVisibility(View.GONE);
//                Bitmap bitmap = captureScreenWindow();
//                facebookButton.setVisibility(View.VISIBLE);
//                ID.setVisibility(View.VISIBLE);
//                Log.d("TAG", "onClick: " + bitmap);
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                File file = BitmapUtil.compressImage(bitmap, getContext());
//                Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.example.assignment3.fileprovider", file);
//                Log.d("TAG", "uriForFile" + uriForFile);
//                Intent intent = new Intent(getActivity(), FacebookActivity.class).putExtra("bitmap", uriForFile.toString());
//                startActivity(intent);
//                fragmentTransaction.commit();
            }
        });

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ReportFragment Report = new ReportFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, Report);
                fragmentTransaction.commit();
            }
        });

        pieChart = root.findViewById(R.id.pieReportChart);
        setupPieChart();
        loadPieChartData();


        final TextView textView = binding.textPieChart;
        pieChartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("TAG", "fragmentonRequestPermissionsResult: ");
        if (grantResults != null && grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case 1: {
                    Log.d("TAG", "onRequestPermissionsResult: ");
                    extracted(facebookButton, id);
                }
                break;
            }
        }
    }

    private void extracted(Button shareButton, Button ID) {
        shareButton.setVisibility(View.GONE);
        ID.setVisibility(View.GONE);
        Bitmap bitmap = captureScreenWindow();
        shareButton.setVisibility(View.VISIBLE);
        ID.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        File file = BitmapUtil.compressImage(bitmap, getContext());
        Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.example.assignment3.fileprovider", file);
        Log.d("TAG", "uriForFile" + uriForFile);
        Intent intent = new Intent(getActivity(), FacebookActivity.class).putExtra("bitmap", uriForFile.toString());
        startActivity(intent);
        fragmentTransaction.commit();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Travel Distance");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);

    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.4f, "12/02/2021"));
        entries.add(new PieEntry(0.6f, "13/02/2021"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "FitBud");
        pieDataSet.setColors(colors);

        PieData data = new PieData(pieDataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
