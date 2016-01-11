package com.example.kp2101.journey0100;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yu on 2016/1/11.
 */
public class ShowMap extends Fragment implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,OnMapReadyCallback{

    MapView mapView;
    GoogleMap map;
    Location currLoc;
    double lat, lon;
    GoogleApiClient googleApiClient;

    public static ShowMap newInstance(double lat , double lon){
        ShowMap myShowMap = new ShowMap();
        Bundle args = new Bundle();
        args.putDouble("lat",lat);
        args.putDouble("lon", lon);
        myShowMap.setArguments(args);
        return myShowMap;
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).)
    }
}
