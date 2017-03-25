package com.wisdom.takeout.app;

import android.app.Application;
import android.content.Context;

import com.wisdom.takeout.module.bean.User;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by HKWisdom on 2017/3/20.
 */

public class TakeoutApp extends Application {

    public static Context sInstance;
    public static User sUser;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sUser = new User();
        sUser.setId(-1);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
