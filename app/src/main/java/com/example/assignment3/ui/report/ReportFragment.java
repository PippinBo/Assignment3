package com.example.assignment3.ui.report;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3.BarChartActivity;
import com.example.assignment3.R;
import com.example.assignment3.databinding.FragmentReportBinding;

import java.util.Calendar;

// this fragment is for report page, feel free to edit

public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;

    // Report Type Input
    String[] reportTypes = {"Bar-Chart","Pie-Chart"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterReportTypes;

    // Start/End Date Input
    private Button startDateButton;
    private Button endDateButton;
    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;

    private Button generateReportButton;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { ReportViewModel reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Report Type Input
        autoCompleteTxt = root.findViewById(R.id.dropMenuReportType);
        adapterReportTypes = new ArrayAdapter<String>(getActivity(),R.layout.list_report_type,reportTypes);
        autoCompleteTxt.setAdapter(adapterReportTypes);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemType = adapterView.getItemAtPosition(i).toString();
            }
        });

        // Start/End Date Picker
        initStartDatePicker();
        initEndDatePicker();
        startDateButton = root.findViewById(R.id.startDatePickerButton);
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDatePickerDialog.show();
            }
        });
        startDateButton.setText(getTodaysDate());
        endDateButton = root.findViewById(R.id.endDatePickerButton);
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDatePickerDialog.show();
            }
        });

        endDateButton.setText(getTodaysDate());

        // Generate Report
        generateReportButton = (Button) root.findViewById(R.id.generateReportButton);
        generateReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((autoCompleteTxt.getText().toString()).equals("Bar-Chart"))
                {
                    openBarChartActivity();
                } else if ((autoCompleteTxt.getText().toString()).equals("Pie-Chart")){
                    openBarChartActivity();
                }
                else{
                    Toast.makeText(getActivity(),"Select Report Type", Toast.LENGTH_SHORT).show();
                }

            }
        });

        final TextView textView = binding.textSlideshow;
        reportViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    // Generate Report
    public void openBarChartActivity(){
        Intent barReportIntent = new Intent(getActivity(), BarChartActivity.class);
        barReportIntent.putExtra("startDate",startDateButton.getText().toString());
        barReportIntent.putExtra("endDate",endDateButton.getText().toString());
        startActivity(barReportIntent);
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

        startDatePickerDialog = new DatePickerDialog(getActivity(),style,dateSetListener, year,month, day);
        startDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

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

        endDatePickerDialog = new DatePickerDialog(getActivity(),style,dateSetListener, year,month, day);
        endDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}