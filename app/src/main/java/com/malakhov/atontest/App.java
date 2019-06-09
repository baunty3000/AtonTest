package com.malakhov.atontest;

import com.vk.sdk.VKSdk;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
