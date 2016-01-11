package com.example.kp2101.journey0100;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;

import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yu on 2016/1/11.
 */


public class LocationActivity extends AppCompatActivity {

    final int LOCATION_CODE = 777;
    double lon;
    double lat;
    ListView listView;
    Button btnOk;
    EditText edtName;

    List<Myplace> myplaces = new ArrayList<Myplace>();
    PlaceAdapter placeAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        lon = Double.parseDouble(getIntent().getExtras().getString("lon"));
        lat = Double.parseDouble(getIntent().getExtras().getString("lat"));
        Log.d("get", String.valueOf(lon));
        Log.d("get", String.valueOf(lat));

        //new GetPlaces().execute();

        getLocation();

        btnOk = (Button) findViewById(R.id.btnOk);
        edtName = (EditText) findViewById(R.id.edtName);
        listView = (ListView) findViewById(R.id.lvLocation);
        placeAdapter = new PlaceAdapter(this, myplaces);
        //Log.d("get", journeys.toString());

        //建立自定Adapter物件

        listView.setAdapter(placeAdapter);

        //點ListView觸發檢視行程
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Myplace p = (Myplace) placeAdapter.getItem(position);
                String lat = String.valueOf(p.getLat());
                String lon = String.valueOf(p.getLon());
                String location = p.getName();
                //Log.d("JA getItem Jid", jId);
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("lat", lat);
                b.putString("lon", lon);
                b.putString("location", location);
                intent.putExtras(b);
//                Intent intent = new Intent(LocationActivity.this, ConsumeAdd.class);
//                intent.putExtra("lat", lat);
//                intent.putExtra("lon", lon);
//                intent.putExtra("location", location);
                LocationActivity.this.setResult(LOCATION_CODE, intent);
                finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("lat", String.valueOf(lat));
                b.putString("lon", String.valueOf(lon));
                b.putString("location", edtName.getText().toString());
                intent.putExtras(b);
                LocationActivity.this.setResult(LOCATION_CODE, intent);
                finish();
            }
        });
    }

    public void getLocation(){
        String placesRequestStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
                "location="+lat+","+lon+
                "&radius=2000"+
                //"&types=food"+
                "&language=zh-tw"+
                "&key=AIzaSyCoceptF2GH93H9KHAPX2xYuoAyrPYDuBQ";

        boolean NODATA = false;
        String result = null;

        Log.d("GetPlaces ", "doInBackground");
        try{
            OkHttpClient mHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(placesRequestStr)
                    .build();
            Response response = mHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                result = response.body().string();
            }
            NODATA=false;
        }catch (IOException e){
            NODATA=true;
        }

        Log.d("GetPlaces ", "onPostExecute");

            if(!NODATA){
                try{
                    JSONArray places = new JSONObject(result).getJSONArray("results");
                    //MarkerOptions[] aPlaceMarkerOpt = new MarkerOptions[places.length()];
                    for(int p=0;p<places.length();p++){
                        JSONObject aPlace = places.getJSONObject(p);
                        //String placeAdd = aPlace.getString("vicinity");
                        //String placeName = aPlace.getString("name");
                        JSONObject loc = aPlace.getJSONObject("geometry").getJSONObject("location");
                        Myplace myplace = new Myplace(Double.valueOf(loc.getString("lat")), Double.valueOf(loc.getString("lng")),
                                aPlace.getString("name"), aPlace.getString("vicinity"));
                        myplaces.add(myplace);
                        //LatLng placell = new LatLng(Double.valueOf(loc.getString("lat")),
                        //       Double.valueOf(loc.getString("lng")));
//                        aPlaceMarkerOpt[p] = new MarkerOptions()
//                                .position(placell)
//                                .title(placeName)
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_point))
//                                .snippet(placeAdd);
//                        placeMarkers[p] = map.addMarker(aPlaceMarkerOpt[p]);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

    }

    class GetPlaces extends AsyncTask {


        String placesRequestStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
                "location="+lat+","+lon+
                "&radius=2000"+
                //"&types=food"+
                "&language=zh-tw"+
                "&key=AIzaSyCoceptF2GH93H9KHAPX2xYuoAyrPYDuBQ";
        private final int MAX_PLACES = 20;
        private Marker[] placeMarkers;
        boolean NODATA = false;
        String result = null;


        @Override
        protected void onPostExecute(Object o){
            Log.d("GetPlaces ", "onPostExecute");

            super.onPostExecute(o);
//            placeMarkers = new Marker[MAX_PLACES];
//            if(placeMarkers!=null){
//                for(int pm=0; pm<placeMarkers.length;pm++){
//                    if(placeMarkers[pm]!=null)
//                        placeMarkers[pm].remove();
//                }
//            }
            if(!NODATA){
                try{
                    JSONArray places = new JSONObject(result).getJSONArray("results");
                    //MarkerOptions[] aPlaceMarkerOpt = new MarkerOptions[places.length()];
                    for(int p=0;p<places.length();p++){
                        JSONObject aPlace = places.getJSONObject(p);
                        //String placeAdd = aPlace.getString("vicinity");
                        //String placeName = aPlace.getString("name");
                        JSONObject loc = aPlace.getJSONObject("geometry").getJSONObject("location");
                        Myplace myplace = new Myplace(Double.valueOf(loc.getString("lat")), Double.valueOf(loc.getString("lng")),
                                aPlace.getString("name"), aPlace.getString("vicinity"));
                        myplaces.add(myplace);
                        //LatLng placell = new LatLng(Double.valueOf(loc.getString("lat")),
                         //       Double.valueOf(loc.getString("lng")));
//                        aPlaceMarkerOpt[p] = new MarkerOptions()
//                                .position(placell)
//                                .title(placeName)
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_point))
//                                .snippet(placeAdd);
//                        placeMarkers[p] = map.addMarker(aPlaceMarkerOpt[p]);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected Object doInBackground(Object[] objects){
            Log.d("GetPlaces ", "doInBackground");
            try{
                OkHttpClient mHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(placesRequestStr)
                        .build();
                Response response = mHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    result = response.body().string();
                }
                NODATA=false;
            }catch (IOException e){
                NODATA=true;
            }
            return null;
        }



    }

}

