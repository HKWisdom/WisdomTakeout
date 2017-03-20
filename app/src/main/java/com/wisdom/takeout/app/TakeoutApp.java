package com.wisdom.takeout.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by HKWisdom on 2017/3/20.
 */

public class TakeoutApp extends Application {

    public static Context sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }
}
