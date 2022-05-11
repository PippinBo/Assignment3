package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private ArrayList barArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = findViewById(R.id.barReportChart);
        loadPieChartData();

        // Intent to receive start and end date
        Intent intent = getIntent();
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");

        TextView barChartTitle = findViewById(R.id.barChartTitle);
        barChartTitle.setText(startDate + " - " + endDate);

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
}