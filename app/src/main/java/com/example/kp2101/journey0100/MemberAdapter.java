package com.example.kp2101.journey0100;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.util.List;

/**
 * Created by KP2101 on 2016/1/6.
 */
public class MemberAdapter extends BaseAdapter {

    private List<Member> members;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    //private int resource;

    public MemberAdapter(Context context, List<Member> members) {

        inflater = LayoutInflater.from(context);
        this.members = members;

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
        return members.size();
    }

    @Override
    public Object getItem(int position) {
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return members.indexOf(getItem(position));
    }

    private class viewHolder{
        ImageView ivuPic;
        TextView txtuId;
        TextView txtuName;
        TextView txtujMoney;
        public viewHolder(ImageView ivuPic, TextView txtuId,TextView txtuName,TextView txtujMoney){
            this.ivuPic = ivuPic;
            this.txtuId = txtuId;
            this.txtuName = txtuName;
            this.txtujMoney = txtujMoney;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;

        //Log.d("getView=", "in");
        if (convertView == null) {
            //Log.d("convertView=", "null");
            // 建立項目畫面元件
            convertView = inflater.inflate(R.layout.member_item, null);
            holder = new viewHolder((ImageView)convertView.findViewById(R.id.ivuPic), (TextView)convertView.findViewById(R.id.txtuId), (TextView)convertView.findViewById(R.id.txtuName), (TextView)convertView.findViewById(R.id.txtujMoney));
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
            //Log.d("convertView=", "not null");
        }

        Member member = (Member) getItem(position);
        holder.txtuId.setText(member.getuId());
        holder.txtuName.setText(member.getuName());
        holder.txtujMoney.setText(member.getujMoney());
        ImageLoader.getInstance().displayImage("http://graph.facebook.com/"+member.getuFbid()+"/picture?width=90&height=90", holder.ivuPic, options, animateFirstListener);

        return convertView;
    }



}
