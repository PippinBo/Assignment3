package com.example.assignment3.ui.map;

import com.google.android.gms.common.api.ApiException;

import com.google.maps.model.LatLng;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.RankBy;

import java.io.IOException;

public class NearlyPlace {

    public static PlacesSearchResponse run(){
        PlacesSearchResponse request = new PlacesSearchResponse();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCXFqRRFtTpVqba8_b6AHnmMqktyMwItMM")
                .build();
        //     LatLng location = new LatLng(-33.865143, 334,417.07)

        String[] latlong =  "-37.915047,145.129272".split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        LatLng location = new LatLng(latitude, longitude);

        try {
            request = PlacesApi.nearbySearchQuery(context, location)
                    .radius(5000)
                    .rankby(RankBy.PROMINENCE)
                    .keyword("gym")
                    .language("en")
                    .type(PlaceType.GYM)
                    .await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        } finally {
            return request;
        }
    }

}
