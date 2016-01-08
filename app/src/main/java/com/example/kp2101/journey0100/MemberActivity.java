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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MemberActivity extends Fragment {

    //撈MemberList
    private ListView lvMember;
    private List<Member> members=null;
    private MemberAdapter memberAdapter=null;

    //從activity傳來的jId
    String jId=null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_activity, container, false);

        //從activity傳來的jId
        Bundle args = getArguments();
        jId = args.getString("jId");
        Log.d("member jId",jId);

        //撈MemberList
        lvMember = (ListView) view.findViewById(R.id.lvMember);
        getMemberList(jId);

        //點右下角按鈕觸發新增成員
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddMember);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent callsub = new Intent();
                callsub.putExtra("jId",jId);
                callsub.setClass(getActivity(), MemberAdd.class);
                startActivity(callsub);

            }
        });




        return view;
    }
    @Override
    public void onStart() {
        getMemberList(jId);
        super.onStart();
    }

    public void getMemberList(String jId){


        members = MemberDB.memberList(jId);
        memberAdapter = new MemberAdapter(getActivity(), members);


        //建立自定Adapter物件
        lvMember.setAdapter(memberAdapter);
        //Log.d("adaper", "after");

    }
}