package com.example.kp2101.journey0100;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KP2101 on 2016/1/7.
 */
public class ConsumeAdd extends AppCompatActivity  implements
        android.widget.CompoundButton.OnCheckedChangeListener {

    Button btnAddC;
    EditText edtCName;
    EditText edtCDollar;
    String cName;
    Double cDollar;
    GlobalVariable globalVariable;

    ListView lvMember;
    List<ConsumeMember> consumemembers=null;
    ConsumeMemberAdapter consumememberAdapter=null;
    Map<Integer,String> checkmember= new HashMap<Integer,String>();

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
        getConsumeMemberList();

        //點ListView觸發檢視行程
        lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //更改總金額觸發
        edtCDollar.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setNeed();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //點確定觸發新增消費
        btnAddC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cName = edtCName.getText().toString();
                cDollar = Double.parseDouble(edtCDollar.getText().toString());

                ConsumeDB.addConsume(globalVariable.jId, cName, cDollar);
                Toast.makeText(ConsumeAdd.this,"新增成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });



    }
    public void getConsumeMemberList(){

        consumemembers = MemberDB.consumeMemberAddList(globalVariable.jId);
        consumememberAdapter = new ConsumeMemberAdapter(this, consumemembers);

        //建立自定Adapter物件
        lvMember.setAdapter(consumememberAdapter);


    }

    //抓有哪幾個member要攤錢
    @Override
    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
        int pos = lvMember.getPositionForView(buttonView);
        if(pos != ListView.INVALID_POSITION){
            ConsumeMember consumemember = consumemembers.get(pos);
            consumemember.setSelected(isChecked);
            //Toast.makeText(this,"Selected:"+consumemember.getuId()+";status:"+isChecked,Toast.LENGTH_SHORT).show();

            //將checked的人存到map中
            if(isChecked){
                checkmember.put(Integer.valueOf(pos),consumemember.getuId());
            }else{
                checkmember.remove(Integer.valueOf(pos));
            }
            setNeed();
        }
    }

    //重新分配每個member的應付金額
    public void setNeed(){
        //Log.d("checkmember", checkmember.toString());
        //Log.d("cDollar", edtCDollar.getText().toString());
        //Log.d("checkmember size", Integer.toString(checkmember.size()));

        //計算平均分攤
        cDollar=Double.parseDouble(edtCDollar.getText().toString());
        double mean=cDollar/(double)checkmember.size();
        //Log.d("mean", String.valueOf(mean));

        //重設每個pos的應付金額
        for(int pos=0;pos<consumemembers.size();pos++) {

            if(checkmember.get(pos)!=null) {
                consumemembers.get(pos).setNeed(mean);
            }else{
                consumemembers.get(pos).setNeed(0.0);
            }
        }

        //更新list
        List<ConsumeMember> cmNew = new ArrayList<ConsumeMember>();
        cmNew.addAll(consumemembers);
        consumememberAdapter.updateReceiptsList(cmNew);
    }

}
