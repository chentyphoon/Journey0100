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

import java.util.List;


public class ConsumeActivity extends Fragment {
        //撈MemberList
        private ListView lvConsume;
        private List<Consume> consumes=null;
        private ConsumeAdapter consumeAdapter=null;
        GlobalVariable globalVariable;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.consume_activity, container, false);



            //抓全域變數uId
            globalVariable = (GlobalVariable)getActivity().getApplicationContext();
            Log.d("globaljId in fragment", globalVariable.jId);

            //撈MemberList
            lvConsume = (ListView) view.findViewById(R.id.lvConsume);
            getConsumeList();

            //點右下角按鈕觸發新增成員
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddConsume);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Intent callsub = new Intent();
                    callsub.setClass(getActivity(), ConsumeAdd.class);
                    startActivity(callsub);

                }
            });

            return view;
        }
        @Override
        public void onStart() {
            getConsumeList();
            super.onStart();
        }

        public void getConsumeList(){

            consumes = ConsumeDB.consumeList(globalVariable.jId);
            consumeAdapter = new ConsumeAdapter(getActivity(), consumes);


            //建立自定Adapter物件
            lvConsume.setAdapter(consumeAdapter);
            //Log.d("adaper", "after");

        }




    }