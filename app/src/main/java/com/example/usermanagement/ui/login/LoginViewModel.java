package com.example.usermanagement.ui.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermanagement.model.LoginResponse;
import com.example.usermanagement.model.User;
import com.example.usermanagement.network.IUserApi;
import com.example.usermanagement.network.RetrofitClient;
import com.example.usermanagement.preferences.PreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    MutableLiveData<LoginResponse> userMutableLiveData = new MutableLiveData<>();

    public void checkLogin(String mail,String password){
        IUserApi iUserApi = RetrofitClient.getClient().create(IUserApi.class);

        iUserApi.loginUser(mail,password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        if(response.body().getSuccess()){
                            PreferenceHelper.saveLoginToken(response.body());
                            PreferenceHelper.saveLoginResponse(response.body());
                            userMutableLiveData.postValue(response.body());
                            Log.i("fail", "Success");
                        }
                    }
                    else {
                        Log.i("fail", "is ...");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("fail", "Failed Api to connect ");
            }
        });
    }



}
