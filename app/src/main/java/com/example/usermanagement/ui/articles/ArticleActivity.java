package com.example.usermanagement.ui.articles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.usermanagement.R;
import com.example.usermanagement.adapter.UserAdapter;
import com.example.usermanagement.adapter.article.ArticleAdapter;
import com.example.usermanagement.databinding.ActivityArticleBinding;
import com.example.usermanagement.databinding.ActivityUserListBinding;
import com.example.usermanagement.model.articles.SingleArticle;
import com.example.usermanagement.ui.user.UserViewModel;

import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    ArticleViewModel articleViewModel;
    ArticleAdapter articleAdapter;
    ActivityArticleBinding activityArticleBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityArticleBinding = ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(activityArticleBinding.getRoot());

        Intent intent = getIntent();
        String idOfUser = intent.getStringExtra("ID");
//        String idOfUser = "1";

        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        articleViewModel.fetchList(idOfUser);

        setRecyclerView();

        articleViewModel.listOfArticles.observe(this, new Observer<List<SingleArticle>>() {
            @Override
            public void onChanged(List<SingleArticle> singleArticles) {
                articleAdapter.updateArticle(singleArticles);
            }
        });

    }

    public void setRecyclerView(){
        articleAdapter = new ArticleAdapter();
        activityArticleBinding.articleRv.setLayoutManager(new LinearLayoutManager(this));
        activityArticleBinding.articleRv.setAdapter(articleAdapter);
    }
}