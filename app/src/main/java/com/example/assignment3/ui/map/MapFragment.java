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
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
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

        Bundle bundle = getActivity().getIntent().getExtras();
        User user = bundle.getParcelable("loginUser");
        String address = user.getAddress();





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
                PlacesSearchResult[] placesSearchResults = NearlyPlace.run().results;
                Context context = requireActivity().getApplicationContext();



                int height = 130;
                int width = 130;
                @SuppressLint("UseCompatLoadingForDrawables")
                BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.ic_gym);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                for (PlacesSearchResult placesSearchResult : placesSearchResults) {
                    double lat = placesSearchResult.geometry.location.lat;
                    double lng = placesSearchResult.geometry.location.lng;

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(placesSearchResult.name)
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    );
                }


                LatLng latLng=getLocationFromAddress(context,
                        address );
                googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng , 12.0f) );
                googleMap.setMinZoomPreference(6.0f);
                googleMap.setMaxZoomPreference(14.0f);

                Button zoomOutButton = (Button) view.findViewById(R.id.zoom_out);
                zoomOutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        googleMap.animateCamera(CameraUpdateFactory.zoomOut());
                    }
                });



                Button zoomInButton = (Button) view.findViewById(R.id.zoom_in);
                zoomInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                    }
                });


                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("You are here")
                        .draggable(true));
            }
        });

         NearlyPlace.run();
//        PlacesSearchResult[] results22 = request.results;
//        String test1 = results22.toString();
//        System.out.println(request);
//        System.out.println(results22);
//        System.out.println(test1);

        // Return view
        return view;
    }


//


    // get the lat and long from address
    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context);
        //if the address is valid use this default address
        String defaultAddress = "350 Bourke Street, Melbourne";
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
