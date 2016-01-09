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
public class ConsumeAdd extends AppCompatActivity {

    Button btnAddC;
    EditText edtCName;
    EditText edtCDollar;
    String cName;
    String cDollar;
    GlobalVariable globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.consume_add);


        globalVariable = (GlobalVariable)getApplicationContext();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("新增");

        btnAddC = (Button) findViewById(R.id.btnAddC);
        edtCName = (EditText) findViewById(R.id.edtCName);
        edtCDollar = (EditText) findViewById(R.id.edtCDollar);


        btnAddC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cName = edtCName.getText().toString();
                cDollar = edtCDollar.getText().toString();

                ConsumeDB.addConsume(globalVariable.jId,cName,cDollar);
                Toast.makeText(ConsumeAdd.this,"新增成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });






    }
}
