package com.example.kp2101.journey0100;

import android.app.Fragment;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.map,container,false);
        mapView = (MapView) v.findViewById(R.id.mvMap);
        return v;
    }

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
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).icon(BitmapDescriptorFactory.fromResource(R.drawable.red_dot)));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
//        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

//    class GetPlaces extends AsyncTask {
//        String placesRequestStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
//                "location="+lat+","+lon+
//                "&radius=2000"+
//                "&language=zh-tw"+
//                "&key=AIzaSyCoceptF2GH93H9KHAPX2xYuoAyrPYDuBQ";
//        private final int MAX_PLACES = 20;
//        private Marker[] placeMarkers;
//        boolean NODATA = false;
//        String result = null;
//
//        @Override
//        protected void onPostExecute(Object o){
//            super.onPostExecute(o);
//            placeMarkers = new Marker[MAX_PLACES];
//            if(placeMarkers!=null){
//                for(int pm=0; pm<placeMarkers.length;pm++){
//                    if(placeMarkers[pm]!=null)
//                        placeMarkers[pm].remove();
//                }
//            }
//            if(!NODATA){
//                try{
//                    JSONArray places = new JSONObject(result).getJSONArray("results");
//                    MarkerOptions[] aPlaceMarkerOpt = new MarkerOptions[places.length()];
//                    for(int p=0;p<places.length();p++){
//                        JSONObject aPlace = places.getJSONObject(p);
//                        String placeAdd = aPlace.getString("vicinity");
//                        String placeName = aPlace.getString("name");
//                        JSONObject loc = aPlace.getJSONObject("geometry").getJSONObject("location");
//                        LatLng placell = new LatLng(Double.valueOf(loc.getString("lat")),
//                                Double.valueOf(loc.getString("lng")));
//                        aPlaceMarkerOpt[p] = new MarkerOptions()
//                                .position(placell)
//                                .title(placeName)
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_point))
//                                .snippet(placeAdd);
//                        placeMarkers[p] = map.addMarker(aPlaceMarkerOpt[p]);
//                    }
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        @Override
//        protected Object doInBackground(Object[] objects){
//            try{
//                OkHttpClient mHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(placesRequestStr)
//                        .build();
//                Response response = mHttpClient.newCall(request).execute();
//                if(response.isSuccessful()){
//                    result = response.body().string();
//                }
//                NODATA=false;
//            }catch (IOException e){
//                NODATA=true;
//            }
//            return null;
//        }
//
//    }
}
