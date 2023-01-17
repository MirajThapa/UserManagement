package com.example.usermanagement.preferences;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.usermanagement.App;
import com.example.usermanagement.constants.PreferenceConstants;
import com.example.usermanagement.model.AllUsers;
import com.example.usermanagement.model.LoginResponse;
import com.google.gson.GsonBuilder;

public class PreferenceHelper {

    public static String getLoginToken() {
        SharedPreferences sharedPreferences = App.getAppPreference();
        String token_id = sharedPreferences.getString(PreferenceConstants.TOKEN, "");
        return token_id;
    }

    public static void saveLoginToken(LoginResponse loginUser) {
        if(loginUser.getToken() != null){
            String tokenId = loginUser.getToken();
            SharedPreferences.Editor editor = App.getAppPreference().edit();
            editor.putString(PreferenceConstants.TOKEN,tokenId);
            editor.apply();
        }
        else {
            Log.e("not", "not saved: ");
        }
    }

    public static void saveLoginResponse(LoginResponse loginResponse){
        SharedPreferences.Editor editor = App.getAppPreference().edit();
        editor.putString(PreferenceConstants.LOGIN_RESPONSE,new GsonBuilder().create().toJson(loginResponse));
        editor.apply();
    }



}
