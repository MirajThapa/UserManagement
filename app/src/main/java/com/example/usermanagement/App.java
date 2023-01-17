package com.example.usermanagement;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.usermanagement.constants.PreferenceConstants;

public class App extends Application {
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences(PreferenceConstants.PREF_FILE, MODE_PRIVATE);
    }

    public static SharedPreferences getAppPreference(){

        return sharedPreferences;
    }

}
