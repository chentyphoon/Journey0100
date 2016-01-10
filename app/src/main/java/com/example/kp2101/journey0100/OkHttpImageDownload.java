package com.example.kp2101.journey0100;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.assist.ContentLengthInputStream;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Yu on 2016/1/9.
 */
public class OkHttpImageDownload extends BaseImageDownloader {
    private OkHttpClient client;

    public OkHttpImageDownload(Context context, OkHttpClient client) {
        super(context);
        this.client = client;
    }

    @Override
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        Request request = new Request.Builder().url(imageUri).build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        InputStream inputStream = responseBody.byteStream();
        int contentLength = (int) responseBody.contentLength();
        return new ContentLengthInputStream(inputStream, contentLength);
    }
}
