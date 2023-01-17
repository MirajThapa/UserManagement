package com.example.usermanagement.network;

import com.example.usermanagement.model.AllUsers;
import com.example.usermanagement.model.articles.Article;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IArticlesApi {

    @FormUrlEncoded
    @POST("userArticles.php")
    Call<Article> getArticles(@Field("user_id") String id);

}
