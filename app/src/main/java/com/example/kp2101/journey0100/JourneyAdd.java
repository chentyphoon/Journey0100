package com.example.kp2101.journey0100;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by KP2101 on 2016/1/7.
 */
public class JourneyAdd  extends AppCompatActivity {

    Button btnAddJ;
    EditText edtJName;
    String jName;
    GlobalVariable globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_add);
        globalVariable = (GlobalVariable) getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("新增旅程");

        btnAddJ = (Button) findViewById(R.id.btnAddJ);
        edtJName = (EditText) findViewById(R.id.edtJName);


        btnAddJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jName = edtJName.getText().toString();
                Log.d("jname=",jName);
                JourneyDB.addJourney(jName,globalVariable.uId);
                Toast.makeText(JourneyAdd.this,"新增成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });






    }
}
