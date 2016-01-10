package com.example.kp2101.journey0100;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;


/**
 * Created by KP2101 on 2016/1/6.
 */
public class JourneyAdapter extends BaseAdapter {

    //private Activity activity;
    private List<Journey> journeys;
    private static LayoutInflater inflater;
    //method1
    public ImageLoader imageLoader;
    //method 2 start
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    //method 2 end
    //private int resource;

    public JourneyAdapter(Context context, List<Journey> journeys) {

        inflater = LayoutInflater.from(context);
        this.journeys = journeys;
        //method1
        //imageLoader = new ImageLoader(context);
        //method2
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(android.R.drawable.ic_menu_gallery)
                .showImageOnFail(android.R.drawable.ic_menu_close_clear_cancel)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();


//        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.loading)
//                .showImageOnFail(android.R.drawable.ic_notification_clear_all)
//                .showImageForEmptyUri(android.R.drawable.ic_menu_add)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
//                .postProcessor(new BitmapProcessor() {
//            @Override
//            public Bitmap process(Bitmap bitmap) {
//                return Bitmap.createScaledBitmap(bitmap, 90, 90, false);
//            }
//        })
//                .build();
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
        TextView txtjId;
        TextView txtjName;
        TextView txtMoney;
        public viewHolder(ImageView ivjPic, TextView txtjId,TextView txtjName, TextView txtMoney){
            this.ivjPic = ivjPic;
            this.txtjId = txtjId;
            this.txtjName = txtjName;
            this.txtMoney = txtMoney;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final viewHolder holder;

        //Log.d("getView=", "in");
        if (convertView == null) {
            //Log.d("convertView=", "null");
            // 建立項目畫面元件
            view = inflater.inflate(R.layout.journey_item, null);
            holder = new viewHolder((ImageView)view.findViewById(R.id.ivjPic), (TextView)view.findViewById(R.id.txtjId),
                    (TextView)view.findViewById(R.id.txtjName), (TextView) view.findViewById(R.id.txtMoney));
            view.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
            //Log.d("convertView=", "not null");
        }

        Journey journey = (Journey) getItem(position);
        holder.txtjId.setText(journey.getjId());
        holder.txtjName.setText(journey.getjName());
        holder.txtMoney.setText(journey.getUjMoney()+"    NT $");
        //method2
        ImageLoader.getInstance().displayImage(journey.getjPic(), holder.ivjPic, options, animateFirstListener);
        //method1
        //imageLoader.DisplayImage(journey.getjPic(), holder.ivjPic);
        return view;
    }

    //method2
//    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
//
//        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//
//        @Override
//        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//            if (loadedImage != null) {
//                ImageView imageView = (ImageView) view;
//                boolean firstDisplay = !displayedImages.contains(imageUri);
//                if (firstDisplay) {
//                    FadeInBitmapDisplayer.animate(imageView, 500);
//                    displayedImages.add(imageUri);
//                }
//            }
//        }
//    }
}

