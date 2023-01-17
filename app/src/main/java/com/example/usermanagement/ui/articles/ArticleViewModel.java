package com.example.usermanagement.ui.articles;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermanagement.model.AllUsers;
import com.example.usermanagement.model.articles.Article;
import com.example.usermanagement.model.articles.SingleArticle;
import com.example.usermanagement.network.IArticlesApi;
import com.example.usermanagement.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleViewModel extends ViewModel {
    MutableLiveData<List<SingleArticle>> listOfArticles =new MutableLiveData<>();

    public void fetchList(String id){
        IArticlesApi articlesApi = RetrofitClient.getClient().create(IArticlesApi.class);
        articlesApi.getArticles(id).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if(response.isSuccessful()){
                    if (response.body() != null){
                        listOfArticles.postValue(response.body().getArticles());
                        Log.i("err", " Articles");
                    }
                }

            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {

                Log.i("err", ""+t.getLocalizedMessage());
                Log.i("err",""+ t.getMessage());
            }
        });
    }
}
