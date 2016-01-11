package com.example.kp2101.journey0100;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.*;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Yu on 2016/1/11.
 */
public class PlaceAdapter extends BaseAdapter{

    private List<Myplace> myplaceList;
    private LayoutInflater inflater;
    //private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    //private DisplayImageOptions options;
    //private int resource;

    public PlaceAdapter(Context context, List<Myplace> myplaceList) {

        inflater = LayoutInflater.from(context);
        this.myplaceList = myplaceList;
    }

    @Override
    public int getCount() {
        return myplaceList.size();
    }

    @Override
    public Object getItem(int position) {
        return myplaceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return myplaceList.indexOf(getItem(position));
    }

    private class viewHolder{

        TextView txtLocation;
        TextView txtAddress;
        public viewHolder(TextView txtLocation,TextView txtAddress){

            this.txtAddress = txtAddress;
            this.txtLocation = txtLocation;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;

        //Log.d("getView=", "in");
        if (convertView == null) {
            //Log.d("convertView=", "null");
            // 建立項目畫面元件
            convertView = inflater.inflate(R.layout.place_item, null);
            holder = new viewHolder((TextView)convertView.findViewById(R.id.txtLocation), (TextView)convertView.findViewById(R.id.txtAddress));
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
            //Log.d("convertView=", "not null");
        }

        Myplace myplace = (Myplace) getItem(position);

        holder.txtLocation.setText(myplace.getName());
        holder.txtAddress.setText(myplace.getAddress());

              return convertView;
    }
}
