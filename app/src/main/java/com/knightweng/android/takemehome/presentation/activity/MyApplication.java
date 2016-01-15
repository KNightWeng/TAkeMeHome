package com.knightweng.android.takemehome.presentation.activity;

import android.app.Application;

import com.knightweng.android.takemehome.utils.VolleyLib;

public class MyApplication extends Application {

    private static MyApplication mMyApplication;

    public MyApplication() {
        mMyApplication = this;
    }

    public static MyApplication getMyApplicationContext() {
        return mMyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyLib.init(this);
    }
}
