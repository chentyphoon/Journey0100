package com.example.kp2101.journey0100;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class JourneyActivity extends AppCompatActivity {

    //撈JourneyList
    private ListView lvJourney;
    private List<Journey> journeys=null;
    private JourneyAdapter journeyAdapter=null;


    GlobalVariable globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_main);

        //抓全域變數uId
        globalVariable = (GlobalVariable) getApplicationContext();
        globalVariable.uId = "4"; //chentyphoon@yahoo.com.tw

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("旅程列表");

        //撈JourneyList
        getJourneyList();

        //點右下角按鈕觸發新增行程
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

        //點ListView觸發檢視行程
        lvJourney.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Journey j = (Journey) journeyAdapter.getItem(position);
                String jId = j.getjId();
                String jName = j.getjName();
                Log.d("JA getItem Jid",jId);
                Intent myIntent = new Intent(JourneyActivity.this,JSubActivity.class);
                myIntent.putExtra("jId", jId);
                myIntent.putExtra("jName", jName);
                startActivity(myIntent);

            }
        });

        lvJourney.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Journey j = (Journey) journeyAdapter.getItem(position);
                String jId = j.getjId();
                JourneyDB.deleteJourney(jId);
                return false;
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

    @Override
    protected void onRestart() {
        getJourneyList();
        //Log.d("JA status","restart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        //Log.d("JA status","resume");
        super.onResume();
    }
    public void getJourneyList(){

        lvJourney = (ListView) findViewById(R.id.lvJourney);
        journeys = JourneyDB.journeyList(globalVariable.uId);
        journeyAdapter = new JourneyAdapter(this, journeys);
        //Log.d("get", journeys.toString());

        //建立自定Adapter物件

        lvJourney.setAdapter(journeyAdapter);
        //Log.d("adaper", "after");

    }
}
