package com.example.usermanagement.ui.articles;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.usermanagement.model.articles.Article;
import com.example.usermanagement.model.articles.SingleArticle;
import com.example.usermanagement.network.IArticlesApi;
import com.example.usermanagement.network.IFetchArticles;
import com.example.usermanagement.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllArticlesViewModel extends ViewModel {
    MutableLiveData<List<SingleArticle>> allArticleList = new MutableLiveData<>(new ArrayList<>());

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getAllArticles(){
        IFetchArticles iArticlesApi = RetrofitClient.getClient().create(IFetchArticles.class);
        Observable<Article> observable = iArticlesApi.getAllArticlesObserable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Article>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Article article) {
                if (article != null && article.getArticles() != null){
                    List<SingleArticle> articles = article.getArticles();
                    allArticleList.postValue(articles);
                    Log.i("ss", String.valueOf(articles.size()));
                }
                else {
                    Log.i("ssss", "empty article");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("ssss", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null){
            compositeDisposable.clear();
            compositeDisposable.dispose();
        }
    }

    public LiveData<List<SingleArticle>> getArticleLiveData(){
        return allArticleList;
    }

}
