package com.malakhov.atontest.common;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.HashMap;

public class DownloaderImages {

    private static final DownloaderImages ourInstance = new DownloaderImages();
    private HashMap<String, Bitmap> mCashImages;
    private ArrayList<DownloadImageTask> mTask;
    private int mQuantityOfImages = 0;
    private final int mCashedImagesThreshold = 10;

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

    public void init(int size){
        if (mCashImages == null){
            mCashImages = new HashMap<>();
        } else {
            if (mCashImages.size() != size){
                mCashImages.clear();
            }
        }
        if (mTask == null){
            mTask = new ArrayList<>();
        } else {
            for (DownloadImageTask task : mTask){
                if (task != null) task.cancel(true);
            }
            mTask.clear();
        }
    }

    public void addToCash(String key, Bitmap result) {
        if (mQuantityOfImages == mCashedImagesThreshold){
            mCashImages.clear();
            mQuantityOfImages = 0;
        }
        mQuantityOfImages++;
        mCashImages.put(key, result);
    }
}
