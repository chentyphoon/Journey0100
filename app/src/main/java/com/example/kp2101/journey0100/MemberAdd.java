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
public class MemberAdd extends AppCompatActivity {

    Button btnAddU;
    EditText edtUId;
    String uId;
    GlobalVariable globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_add);

        final String jId = getIntent().getExtras().getString("jId");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("新增成員");

        btnAddU = (Button) findViewById(R.id.btnAddU);
        edtUId = (EditText) findViewById(R.id.edtUId);


        btnAddU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uId = edtUId.getText().toString();
                Log.d("uId=",uId);
                MemberDB.addMemberToJourney(uId,jId);
                Toast.makeText(MemberAdd.this,"新增成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });






    }
}
