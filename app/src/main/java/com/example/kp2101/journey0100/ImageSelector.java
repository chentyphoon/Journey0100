package com.example.kp2101.journey0100;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by Yu on 2016/1/10.
 */
public class ImageSelector{

    final static int SELECT_PHOTO = 100;
    final static int CROP_OK = 111;

//    public ImageSelector(Context context){
//        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
//            GlobalVariable.cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"Journey");
//        else
//            GlobalVariable.cacheDir=context.getCacheDir();
//        if(!GlobalVariable.cacheDir.exists())
//            GlobalVariable.cacheDir.mkdirs();
//    }

    public static Intent select() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setType("image/*");

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, pickIntent);
        chooser.putExtra(Intent.EXTRA_TITLE, "Select from:");

        Intent[] intentArray = {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        return  chooser;
    }

    public static Intent doCrop(Uri imageUri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setData(imageUri);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //是否将数据保留在Bitmap中返回
        intent.putExtra("return-data", true);
        return intent;
    }

    public static Uri bitmapToFile(Bitmap bitmap){
        String fileName = new Timestamp(System.currentTimeMillis()).toString()+".jpg";
        File f = new File(GlobalVariable.cacheDir, fileName);
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Uri.fromFile(f);
    }
}
