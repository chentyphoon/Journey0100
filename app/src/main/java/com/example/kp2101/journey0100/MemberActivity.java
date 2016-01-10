package com.example.kp2101.journey0100;

/**
 * Created by TonyLabNew on 2016/1/8.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MemberActivity extends Fragment {
    //撈MemberList
    private ListView lvMember;
    private List<Member> members=null;
    private MemberAdapter memberAdapter=null;

    private Member jMe=null;
    TextView txtjMeName;
    TextView txtjMeMoney;

    GlobalVariable globalVariable;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_activity, container, false);



        //抓全域變數uId
        globalVariable = (GlobalVariable)getActivity().getApplicationContext();
        Log.d("globaljId in fragment", globalVariable.jId);


        //撈Me
        txtjMeName = (TextView) view.findViewById(R.id.txtjMeName);
        txtjMeMoney = (TextView) view.findViewById(R.id.txtjMeMoney);
        jMe=MemberDB.memberMe(globalVariable.jId,globalVariable.uId);
        txtjMeName.setText(jMe.getuName());
        txtjMeMoney.setText(jMe.getujMoney()+"   NT $");


        //撈MemberList
        lvMember = (ListView) view.findViewById(R.id.lvMember);
        getMemberList();

        //點ListView觸發交易
        lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member m = (Member) memberAdapter.getItem(position);
                String uIdTo = m.getuId();
                String uNameTo = m.getuName();
                Intent myIntent = new Intent(getActivity(), transActivity.class);
                myIntent.putExtra("uIdTo", uIdTo);
                myIntent.putExtra("uNameTo", uNameTo);
                startActivity(myIntent);

            }
        });

        //點右下角按鈕觸發新增成員
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddMember);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent callsub = new Intent();
                callsub.setClass(getActivity(), MemberAdd.class);
                startActivity(callsub);

            }
        });

        return view;
    }
    @Override
    public void onStart() {
        getMemberList();
        super.onStart();
    }

    public void getMemberList(){

        members = MemberDB.memberList(globalVariable.jId,globalVariable.uId);
        memberAdapter = new MemberAdapter(getActivity(), members);

        //建立自定Adapter物件
        lvMember.setAdapter(memberAdapter);
        //Log.d("adaper", "after");

    }




}