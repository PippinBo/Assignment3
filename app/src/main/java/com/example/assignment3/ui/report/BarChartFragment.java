package com.example.assignment3.ui.report;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.example.assignment3.databinding.FragmentBarchartBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.util.ArrayList;

// this fragment is for report page, feel free to edit

public class BarChartFragment extends Fragment {

    private FragmentBarchartBinding binding;
    private BarChart barChart;
    private ArrayList barArrayList;
    private Bitmap bitmap;
    private Button shareButton;
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
        BarChartViewModel barChartViewModel =
                new ViewModelProvider(this).get(BarChartViewModel.class);

        binding = FragmentBarchartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        id = (Button) root.findViewById(R.id.back_button);
        shareButton = (Button) root.findViewById(R.id.facebook_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //请求权限
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    ActivityCompat.requestPermissions(getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //调用
                    extracted(shareButton, id);
                }

            }
        });


        barChart = root.findViewById(R.id.barReportChart);
        loadPieChartData();


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

        final TextView textView = binding.generateReport;
        barChartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("TAG", "fragmentonRequestPermissionsResult: ");
        if (grantResults != null && grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case 1: {
                    Log.d("TAG", "onRequestPermissionsResult: ");
                    extracted(shareButton, id);
                }
                break;
            }
        }
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        getData();
        BarDataSet barDataSet = new BarDataSet(barArrayList, "FitBud");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

    }

    private void getData() {
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry(2f, 10));
        barArrayList.add(new BarEntry(3f, 20));
        barArrayList.add(new BarEntry(4f, 30));
        barArrayList.add(new BarEntry(5f, 40));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}