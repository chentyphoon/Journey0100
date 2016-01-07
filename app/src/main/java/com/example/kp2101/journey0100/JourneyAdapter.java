package com.example.kp2101.journey0100;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by KP2101 on 2016/1/6.
 */
public class JourneyAdapter extends BaseAdapter {

    private List<Journey> journeys;
    private LayoutInflater inflater;

    //private int resource;

    public JourneyAdapter(Context context, List<Journey> journeys) {

        inflater = LayoutInflater.from(context);
        this.journeys = journeys;
    }

    @Override
    public int getCount() {
        return journeys.size();
    }

    @Override
    public Object getItem(int position) {
        return journeys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return journeys.indexOf(getItem(position));
    }

    private class viewHolder{
        ImageView ivjPic;
        TextView txtjName;
        public viewHolder(ImageView ivjPic, TextView txtjName){
            this.ivjPic = ivjPic;
            this.txtjName = txtjName;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;

        Log.d("getView=", "in");
        if (convertView == null) {
            Log.d("convertView=", "null");
            // 建立項目畫面元件
            convertView = inflater.inflate(R.layout.journey_item, null);
            holder = new viewHolder((ImageView)convertView.findViewById(R.id.ivjPic), (TextView)convertView.findViewById(R.id.txtjName));
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
            Log.d("convertView=", "not null");
        }

        Journey journey = (Journey) getItem(position);

        holder.txtjName.setText(journey.getjName());

        return convertView;
    }

}
