package com.example.assignment3.ui.map;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
import com.example.assignment3.entity.Movement;
import com.example.assignment3.entity.User;
import com.example.assignment3.ui.record.RecordViewModel;
import com.example.assignment3.ui.report.FacebookActivity;
import com.example.assignment3.viewmodel.UserViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;


import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        Context context = requireActivity().getApplicationContext();

        Bundle bundle = getActivity().getIntent().getExtras();
        User user = bundle.getParcelable("loginUser");
        String address = user.getAddress();


        // Async map
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                LatLng latLng=getLocationFromAddress(context,
                        address );
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                PlacesSearchResult[] placesSearchResults = NearlyPlace.run(latitude,longitude).results;
                int height = 110;
                int width = 110;
                @SuppressLint("UseCompatLoadingForDrawables")
                BitmapDrawable bitMapDraw1 = (BitmapDrawable)getResources().getDrawable(R.drawable.gym1);
                @SuppressLint("UseCompatLoadingForDrawables")
                BitmapDrawable bitMapDraw2 = (BitmapDrawable)getResources().getDrawable(R.drawable.gym2);
                @SuppressLint("UseCompatLoadingForDrawables")
                BitmapDrawable bitMapDraw3 = (BitmapDrawable)getResources().getDrawable(R.drawable.ic_user);

                Bitmap b1 = bitMapDraw1.getBitmap();
                Bitmap b2 = bitMapDraw2.getBitmap();
                Bitmap b3 = bitMapDraw3.getBitmap();
                Bitmap APIGymLogo = Bitmap.createScaledBitmap(b1, width, height, false);
                Bitmap UserGymLogo = Bitmap.createScaledBitmap(b2, width, height, false);
                Bitmap UserLogo = Bitmap.createScaledBitmap(b3, width, height, false);

                for (PlacesSearchResult placesSearchResult : placesSearchResults) {
                    double lat = placesSearchResult.geometry.location.lat;
                    double lng = placesSearchResult.geometry.location.lng;

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(placesSearchResult.name)
                            .icon(BitmapDescriptorFactory.fromBitmap(APIGymLogo))
                    );
                }

                userViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        for (int i=0; i < users.size(); i++) {
                            if (users.get(i).getRole().equals("Gym")) {
                                LatLng latLngGym = getLocationFromAddress(context,users.get(i).getAddress());
                                System.out.println(latLngGym);
                                googleMap.addMarker(new MarkerOptions()
                                        .position(latLngGym)
                                        .title(users.get(i).getName())
                                        .icon(BitmapDescriptorFactory.fromBitmap(UserGymLogo))
                                );
                            }
                        }
                    }
                });

                if (!latLng.equals(new LatLng (-37.8136663,144.9633777))){
                    googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng , 12.0f) );
                    googleMap.setMinZoomPreference(6.0f);
                    googleMap.setMaxZoomPreference(14.0f);
                    Marker userLocation = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("You are here")
                            .draggable(true)
                            .icon(BitmapDescriptorFactory.fromBitmap(UserLogo))
                    );
                    assert userLocation != null;
                    userLocation.showInfoWindow();
                }
                else {
                    googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng, 12.0f) );
                    googleMap.setMinZoomPreference(6.0f);
                    googleMap.setMaxZoomPreference(14.0f);
                    Marker userLocation = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Position not find/default position")
                            .draggable(true)

                    );
                    assert userLocation != null;
                    userLocation.showInfoWindow();
                }


//                googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng , 12.0f) );
//                googleMap.setMinZoomPreference(6.0f);
//                googleMap.setMaxZoomPreference(14.0f);

                Button zoomOutButton = (Button) view.findViewById(R.id.zoom_out);
                zoomOutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        googleMap.animateCamera(CameraUpdateFactory.zoomOut());
                    }
                });

                ImageButton locationButton = (ImageButton) view.findViewById(R.id.location);
                locationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng , 12.0f) );
                    }
                });



                Button zoomInButton = (Button) view.findViewById(R.id.zoom_in);
                zoomInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                    }
                });

            }
        });
        return view;
    }


//


    // get the lat and long from address
    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context);
        //if the address is valid use this default address
        String defaultAddress = "350 Bourke Street Melbourne VIC 3000";
        List<Address> addressList;
        LatLng latLng = null;
            try {
                addressList = geocoder.getFromLocationName(strAddress, 1);
                if (addressList!=null) {
                    // This try catch can handle IndexOutOfBoundsException
                    // Because if address is valid List<Address>.get(0)
                    // will through IndexOutOfBoundsException
                    try {
                        Address location = addressList.get(0);
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    } catch (IndexOutOfBoundsException ex1) {
                        addressList = geocoder.getFromLocationName(defaultAddress, 1);
                        Address location = addressList.get(0);
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
            return latLng;
        }


}
