package com.example.assignment3.ui.map;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment3.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(-37.915047, 145.129272) , 12.0f) );
                googleMap.setMinZoomPreference(6.0f);
                googleMap.setMaxZoomPreference(14.0f);

                LatLng user1 = new LatLng( -37.915047, 145.129272);
                googleMap.addMarker(new MarkerOptions()
                        .position(user1)
                        .title("Marker in user1"));
            }
        });
        // Return view
        return view;
    }
}
