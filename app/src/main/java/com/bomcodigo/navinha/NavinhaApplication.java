package com.bomcodigo.navinha;

import android.app.Application;
import android.content.Context;
import android.util.Log;


public class NavinhaApplication extends Application {
    private static final String TAG = NavinhaApplication.class.getSimpleName();
    public static Context applicationContext;

    public NavinhaApplication() {}

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.applicationContext = getApplicationContext();
    }


}
