package com.example.assignment3.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment3.databinding.FragmentRecordBinding;
import com.example.assignment3.databinding.NavHeaderMainBinding;
import com.example.assignment3.entity.User;
import com.example.assignment3.ui.record.RecordViewModel;

public class DrawerHead extends Fragment {

    private NavHeaderMainBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = NavHeaderMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Bundle bundle = getActivity().getIntent().getExtras();
        User user = bundle.getParcelable("loginUser");
        String address = user.getAddress();
        binding.userName.setText(address);

//        final TextView textView = binding.userName;
//        recordViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
