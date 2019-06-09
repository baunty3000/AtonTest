package com.malakhov.atontest.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView mImage;
    private ProgressBar mProgressBar;

    public DownloadImageTask(ImageView bmImage, ProgressBar progressBar) {
        this.mImage = bmImage;
        this.mProgressBar = progressBar;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("info", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        mImage.setImageBitmap(result);
        mProgressBar.setVisibility(View.GONE);
    }
}