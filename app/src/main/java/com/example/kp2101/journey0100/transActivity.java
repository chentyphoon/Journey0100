package com.example.kp2101.journey0100;



import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by TonyLabNew on 2016/1/8.
 */
public class transActivity extends AppCompatActivity {
    String uIdTo;
    String uNameTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_main);

        //抓全域變數
        GlobalVariable globalVariable = (GlobalVariable)getApplicationContext();

        //抓上一個activity傳來的uIdTo
        uIdTo = getIntent().getExtras().getString("uIdTo");
        uNameTo = getIntent().getExtras().getString("uNameTo");


        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("交易 > " + uNameTo);








    }
}
