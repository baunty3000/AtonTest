package com.malakhov.atontest.common;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.HashMap;

public class DownloaderImages {

    private static final DownloaderImages ourInstance = new DownloaderImages();

    private HashMap<String, Bitmap> mCashImages;
    private ArrayList<DownloadImageTask> mTask;

    private DownloaderImages() {}

    public static DownloaderImages getInstance() {
        return ourInstance;
    }

    public Bitmap getPhoto(String key) {
        return mCashImages.get(key);
    }

    public void downloadPhoto(String photoOrig, LoadImageCallBack loadImageCallBack) {
        mTask.add((DownloadImageTask) new DownloadImageTask(loadImageCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, photoOrig));
    }

    public void init(){
        if (mCashImages == null||mCashImages.size()==0){
            mCashImages = new HashMap<>();
            mTask = new ArrayList<>();
        }
    }

    public void addToCash(String key, Bitmap result) {
        mCashImages.put(key, result);
    }
}
