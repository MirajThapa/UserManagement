package com.example.usermanagement.ui.articles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.usermanagement.adapter.article.ArticleAdapter;
import com.example.usermanagement.databinding.ActivityAllArticlesBinding;
import com.example.usermanagement.model.articles.Article;
import com.example.usermanagement.model.articles.SingleArticle;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class AllArticlesActivity extends AppCompatActivity {
    ActivityAllArticlesBinding activityAllArticlesBinding;
    AllArticlesViewModel allArticlesViewModel;
    ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAllArticlesBinding = ActivityAllArticlesBinding.inflate(getLayoutInflater());
        setContentView(activityAllArticlesBinding.getRoot());

        allArticlesViewModel = new ViewModelProvider(this).get(AllArticlesViewModel.class);
        allArticlesViewModel.getAllArticles();

        setRecyclerView();

        allArticlesViewModel.getArticleLiveData().observe(this, new Observer<List<SingleArticle>>() {
            @Override
            public void onChanged(List<SingleArticle> singleArticles) {
                articleAdapter.updateArticle(singleArticles);
            }
        });

    }

    private void setRecyclerView() {
        articleAdapter = new ArticleAdapter();
        activityAllArticlesBinding.allArticlesRv.setLayoutManager(new LinearLayoutManager(this));
        activityAllArticlesBinding.allArticlesRv.setAdapter(articleAdapter);
    }
}