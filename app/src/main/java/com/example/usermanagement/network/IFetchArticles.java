package com.example.usermanagement.network;

import androidx.lifecycle.LiveData;

import com.example.usermanagement.model.articles.Article;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IFetchArticles {
    @GET("fetchArticles.php")
    LiveData<Article> getAllArticles();

    @GET("fetchArticles.php")
    Observable<Article> getAllArticlesObserable();
}
