package com.example.usermanagement.ui.user;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermanagement.model.AllUsers;
import com.example.usermanagement.model.User;
import com.example.usermanagement.network.IUserApi;
import com.example.usermanagement.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    MutableLiveData<List<User>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<User>> getUserLiveData(){
        return mutableLiveData;
    }

    public void getAllUsers(){
        IUserApi iUserApi = RetrofitClient.getClient().create(IUserApi.class);

        iUserApi.getUsers().enqueue(new Callback<AllUsers>() {
            @Override
            public void onResponse(Call<AllUsers> call, Response<AllUsers> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        mutableLiveData.postValue(response.body().getUsers());
                    }
                }else {
                    Log.i("aaaa", "is notSuccess");
                }
            }

            @Override
            public void onFailure(Call<AllUsers> call, Throwable t) {
                Log.i("aaaa", "response failed"+t.getLocalizedMessage());
                Log.i("aaaa", "response failed"+t.getMessage());
            }
        });

    }
}
