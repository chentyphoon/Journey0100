package com.example.kp2101.journey0100;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by KP2101 on 2016/1/7.
 */
public class JourneyAdd  extends AppCompatActivity {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.journey_add);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("test");
}
