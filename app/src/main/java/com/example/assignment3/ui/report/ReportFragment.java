package com.example.assignment3.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3.R;
import com.example.assignment3.databinding.FragmentReportBinding;

// this fragment is for report page, feel free to edit

public class ReportFragment extends Fragment {

    String[] reportTypes = {"Bar-Chart","Pie-Chart"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterReportTypes;

    private FragmentReportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { ReportViewModel reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        autoCompleteTxt = root.findViewById(R.id.dropMenuReportType);
        adapterReportTypes = new ArrayAdapter<String>(getActivity(),R.layout.list_report_type,reportTypes);
        autoCompleteTxt.setAdapter(adapterReportTypes);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemType = adapterView.getItemAtPosition(i).toString();
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