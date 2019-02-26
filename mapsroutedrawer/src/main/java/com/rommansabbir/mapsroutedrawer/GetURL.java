package com.rommansabbir.mapsroutedrawer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class GetURL extends AppCompatActivity {
    private Context context;

    /**
     * @param context
     */
    public GetURL(Context context) {
        this.context = context;
    }

    /**
     * @param apiKey
     * @param origin
     * @param dest
     * @param directionMode
     * @return
     */
    public String getUrl(Integer apiKey, LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + context.getString(apiKey);
        return url;
    }
}
