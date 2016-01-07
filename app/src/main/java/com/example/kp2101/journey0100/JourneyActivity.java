package com.example.kp2101.journey0100;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class JourneyActivity extends AppCompatActivity {

    private ListView lvJourney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("旅程列表");


        

        JourneyDB journeyDB = new JourneyDB();
        lvJourney = (ListView) findViewById(R.id.lvJourney);
        List<Journey> journeys = journeyDB.journeyList();
        Log.d("get", journeys.toString());

        //建立自定Adapter物件
        JourneyAdapter journeyAdapter = new JourneyAdapter(this, journeys);
        lvJourney.setAdapter(journeyAdapter);
        Log.d("adaper", "after");








        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddJourney);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent callsub = new Intent();
                callsub.setClass(JourneyActivity.this,JourneyAdd.class);
                startActivity(callsub);

            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
