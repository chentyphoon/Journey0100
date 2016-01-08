package com.example.kp2101.journey0100;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by TonyLabNew on 2016/1/8.
 */
public class JSubActivity extends AppCompatActivity {
    String jId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsub_main);

        //抓全域變數uId
        GlobalVariable globalVariable = (GlobalVariable)getApplicationContext();

        //抓上一個activity傳來的jId
        jId = getIntent().getExtras().getString("jId");





    }
}
