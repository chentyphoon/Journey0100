package com.example.kp2101.journey0100;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by KP2101 on 2016/1/6.
 */
public class ConsumeAdapter extends BaseAdapter {

    private List<Consume> consumes;
    private LayoutInflater inflater;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    //private int resource;

    public ConsumeAdapter(Context context, List<Consume> consumes) {

        inflater = LayoutInflater.from(context);
        this.consumes = consumes;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(android.R.drawable.ic_menu_gallery)
                .showImageOnFail(android.R.drawable.ic_menu_close_clear_cancel)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                        //.displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
    }

    @Override
    public int getCount() {
        return consumes.size();
    }

    @Override
    public Object getItem(int position) {
        return consumes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return consumes.indexOf(getItem(position));
    }

    private class viewHolder{
        ImageView ivcPic;
        TextView txtcId;
        TextView txtcName;
        TextView txtcDollar;
        public viewHolder(ImageView ivcPic, TextView txtcId,TextView txtcName,TextView txtcDollar){
            this.ivcPic = ivcPic;
            this.txtcId = txtcId;
            this.txtcName = txtcName;
            this.txtcDollar = txtcDollar;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;

        //Log.d("getView=", "in");
        if (convertView == null) {
            //Log.d("convertView=", "null");
            // 建立項目畫面元件
            convertView = inflater.inflate(R.layout.consume_item, null);
            holder = new viewHolder((ImageView)convertView.findViewById(R.id.ivcPic), (TextView)convertView.findViewById(R.id.txtcId), (TextView)convertView.findViewById(R.id.txtcName), (TextView)convertView.findViewById(R.id.txtcDollar));
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
            //Log.d("convertView=", "not null");
        }

        Consume consume = (Consume) getItem(position);
        holder.txtcId.setText(consume.getcId());
        holder.txtcName.setText(consume.getcName());
        holder.txtcDollar.setText(consume.getcDollar());

        ImageLoader.getInstance().displayImage(consume.getcPic(), holder.ivcPic, options, animateFirstListener);

        return convertView;
    }

}
