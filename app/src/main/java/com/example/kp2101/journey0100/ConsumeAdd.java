package com.example.kp2101.journey0100;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

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

    private ListView lvMember;
    private List<Member> members=null;
    private ConsumeMemberAdapter consumememberAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.consume_add);


        globalVariable = (GlobalVariable)getApplicationContext();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("新增消費");

        btnAddC = (Button) findViewById(R.id.btnAddC);
        edtCName = (EditText) findViewById(R.id.edtCName);
        edtCDollar = (EditText) findViewById(R.id.edtCDollar);




        lvMember = (ListView) findViewById(R.id.lvMember);
        getMemberList();

        //點ListView觸發檢視行程
        lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Journey j = (Journey) consumememberAdapter.getItem(position);
                String jId = j.getjId();
                String jName = j.getjName();
                Log.d("JA getItem Jid", jId);


            }
        });


        btnAddC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cName = edtCName.getText().toString();
                cDollar = edtCDollar.getText().toString();

                ConsumeDB.addConsume(globalVariable.jId, cName, cDollar);
                Toast.makeText(ConsumeAdd.this,"新增成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });



    }
    public void getMemberList(){

        members = MemberDB.memberList(globalVariable.jId);
        consumememberAdapter = new ConsumeMemberAdapter(this, members);


        //建立自定Adapter物件
        lvMember.setAdapter(consumememberAdapter);
        //Log.d("adaper", "after");

    }
}
