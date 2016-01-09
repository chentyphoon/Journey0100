package com.example.kp2101.journey0100;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by KP2101 on 2016/1/6.
 */
public class ConsumeMemberAdapter extends ArrayAdapter {


    public List<ConsumeMember> consumemembers;
    private LayoutInflater inflater;
    private Context context;

    //private int resource;

    public ConsumeMemberAdapter(Context context, List<ConsumeMember> consumemembers) {
        super(context,R.layout.member_item_for_consume,consumemembers);

        inflater = LayoutInflater.from(context);
        this.context= context;
        this.consumemembers = consumemembers;
    }

    @Override
    public int getCount() {
        return consumemembers.size();
    }

    public void updateReceiptsList(List<ConsumeMember> newlist) {
        consumemembers.clear();
        consumemembers.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return consumemembers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return consumemembers.indexOf(getItem(position));
    }

    private class viewHolder{
        ImageView ivuPic;
        TextView txtuId;
        TextView txtuName;
        CheckBox cbNeed;
        EditText edtNeed;
        EditText edtPaid;
        public viewHolder(ImageView ivuPic, TextView txtuId,TextView txtuName,CheckBox cbNeed,EditText edtNeed,EditText edtPaid){
            this.ivuPic = ivuPic;
            this.txtuId = txtuId;
            this.txtuName = txtuName;
            this.cbNeed = cbNeed;
            this.edtNeed = edtNeed;
            this.edtPaid = edtPaid;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos1=position;
        final View v=convertView;
        viewHolder holder = null;

        //Log.d("getView=", "in");
        if (convertView == null) {
            //Log.d("convertView=", "null");
            // 建立項目畫面元件

            convertView = inflater.inflate(R.layout.member_item_for_consume, null);

            holder = new viewHolder((ImageView)convertView.findViewById(R.id.ivuPic), (TextView)convertView.findViewById(R.id.txtuId),
                    (TextView)convertView.findViewById(R.id.txtuName), (CheckBox)convertView.findViewById(R.id.cbNeed),
                    (EditText)convertView.findViewById(R.id.edtNeed),(EditText)convertView.findViewById(R.id.edtPaid));
            holder.cbNeed.setOnCheckedChangeListener((ConsumeAdd) context);

            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
            //Log.d("convertView=", "not null");
        }

        final ConsumeMember consumemember = (ConsumeMember) getItem(position);

        holder.txtuId.setText(consumemember.getuId());
        holder.txtuName.setText(consumemember.getuName());
        holder.edtNeed.setText(String.valueOf(consumemember.getNeed()));
        holder.edtPaid.setText(String.valueOf(consumemember.getPaid()));
        holder.cbNeed.setChecked(consumemember.isSelected());
        holder.cbNeed.setTag(consumemember);



//        final EditText fedtNeed=holder.edtNeed;
//        fedtNeed.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//
//                //Log.d("On text changed", s+",Start:"+start+",count:"+count+",before:"+before);
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                //Log.d("Before text changed", s+",Start:"+start+",count:"+count+",after:"+after);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Integer pos = (Integer)v.getTag();
//                Log.d("get pos",String.valueOf(pos));
//                if (s.length() != 0) {
//                    double need = Double.parseDouble(s.toString());
//                    //consumemember.setNeed(need);
//                    consumemembers.get(pos).setNeed(need);
//                    Log.d("textchange","pos="+pos+";need="+String.valueOf(consumemembers.get(pos).getNeed()));
//                } else {
//
//                }
//
//            }
//        });



        return convertView;
    }



}
