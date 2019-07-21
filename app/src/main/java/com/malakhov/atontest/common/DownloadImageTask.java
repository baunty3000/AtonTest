package com.malakhov.atontest.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private LoadImageCallBack mLoadImageCallBack;

    public DownloadImageTask(LoadImageCallBack loadImageCallBack) {
        mLoadImageCallBack = loadImageCallBack;
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(urls[0]).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("info", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        if (mLoadImageCallBack != null){
            mLoadImageCallBack.loadedImage(result);
            mLoadImageCallBack = null;
        }
    }

    @Override
    protected void onCancelled(Bitmap bitmap) {
        super.onCancelled(bitmap);
        mLoadImageCallBack = null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mLoadImageCallBack = null;
    }
}