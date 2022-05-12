package com.example.assignment3.ui.map;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment3.R;
import com.example.assignment3.entity.User;
import com.example.assignment3.ui.record.RecordViewModel;
import com.example.assignment3.viewmodel.UserViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

//version 1.0.1: map fragment --Hongyu
public class MapFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.fragment_map, container, false);
        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);


        UserViewModel userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(UserViewModel.class);

        Bundle bundle = getActivity().getIntent().getExtras();
        User user = bundle.getParcelable("loginUser");
        String address = user.getAddress();
//        System.out.println(address);


//
//
//        String email = "test1@gmail.com";
//        userViewModel.findByEmail(email).observe(getViewLifecycleOwner(), new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//               String address = user.getAddress();
//            }
//        });

        // Async map
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(-37.915047, 145.129272) , 12.0f) );
                googleMap.setMinZoomPreference(6.0f);
                googleMap.setMaxZoomPreference(14.0f);


                Context context = requireActivity().getApplicationContext();
                LatLng latLng=getLocationFromAddress(context,
                       address );

                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Marker in user1")
                        .draggable(true));
            }
        });

        // Return view
        return view;
    }



    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList;
        LatLng latLng = null;
        try {
            addressList = geocoder.getFromLocationName(strAddress, 1);
            if (addressList!=null) {
                Address location = addressList.get(0);
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return latLng;
    }


//
//    public GeoPoint getLocationFromAddress(Context context, String strAddress) {
//
//        Geocoder coder = new Geocoder(context);
//        List<Address> address;
//        GeoPoint p1 = null;
//
//        try {
//            address = coder.getFromLocationName(strAddress, 5);
//            if (address == null) {
//                return null;
//            }
//            Address location = address.get(0);
//            location.getLatitude();
//            location.getLongitude();
//
//            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
//                    (double) (location.getLongitude() * 1E6));
//
//            return p1;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
