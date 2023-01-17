package com.example.usermanagement.network;

import com.example.usermanagement.model.LoginResponse;
import com.example.usermanagement.model.AllUsers;
import com.example.usermanagement.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IUserApi {
    @GET("fetchUser.php")
    Call<AllUsers> getUsers();

    @FormUrlEncoded
    @POST("registration.php")
    Call<ResponseBody> registerUser(@Field("email") String email,
                                    @Field("username") String username,
                                    @Field("password") String password,
                                    @Field("gender") String gender,
                                    @Field("photo") String photo);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(@Field("email") String email,
                                  @Field("password") String password);
}
