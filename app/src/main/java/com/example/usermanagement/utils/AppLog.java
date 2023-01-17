package com.example.usermanagement.utils;

import android.util.Log;

import com.example.usermanagement.BuildConfig;

public class AppLog {

    private static final String TAG = AppLog.class.getSimpleName();

    public static void showLog( String message) {
//        if (BuildConfig.DEBUG){
//
//        }
        Log.e(TAG, "showLog: " + message);
    }
}
