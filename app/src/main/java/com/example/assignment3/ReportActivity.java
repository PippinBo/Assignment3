package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {

    String[] reportTypes = {"Bar-Chart","Pie-Chart"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterReportTypes;

    private Button startDateButton;
    private Button endDateButton;
    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;

    private Button generateReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Start and End Date Picker
        initStartDatePicker();
        initEndDatePicker();
        startDateButton = findViewById(R.id.startDatePickerButton);
        startDateButton.setText(getTodaysDate());
        endDateButton = findViewById(R.id.endDatePickerButton);
        endDateButton.setText(getTodaysDate());


        // Report Type Picker
        autoCompleteTxt = findViewById(R.id.dropMenuReportType);
        adapterReportTypes = new ArrayAdapter<String>(this,R.layout.list_report_type,reportTypes);
        autoCompleteTxt.setAdapter(adapterReportTypes);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemType = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),"Item:" + itemType, Toast.LENGTH_SHORT).show();
            }
        });

        // Generate Report
        generateReportButton = (Button) findViewById(R.id.generateReportButton);
        generateReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((autoCompleteTxt.getText().toString()).equals("Bar-Chart"))
                {
                    openBarChartActivity();
                } else if ((autoCompleteTxt.getText().toString()).equals("Pie-Chart")){
                    openPieChartActivity();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Select Report Type", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Generate Report
    public void openBarChartActivity(){
        Intent barReportIntent = new Intent(this, BarChartActivity.class);
        barReportIntent.putExtra("startDate",startDateButton.getText().toString());
        barReportIntent.putExtra("endDate",endDateButton.getText().toString());
        startActivity(barReportIntent);
    }

    public void openPieChartActivity(){
        Intent pieReportIntent = new Intent(this, PieChartActivity.class);
        pieReportIntent.putExtra("startDate",startDateButton.getText().toString());
        pieReportIntent.putExtra("endDate",endDateButton.getText().toString());
        startActivity(pieReportIntent);
    }




    // Start and End Date Selection

    private void initEndDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day,month,year);
                endDateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        endDatePickerDialog = new DatePickerDialog(this,style,dateSetListener, year,month, day);
        endDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void initStartDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day,month,year);
                startDateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        startDatePickerDialog = new DatePickerDialog(this,style,dateSetListener, year,month, day);
        startDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }

    public void openStartDatePicker(View view){
        startDatePickerDialog.show();
    }

    public void openEndDatePicker(View view){
        endDatePickerDialog.show();
    }
}
