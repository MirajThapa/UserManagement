package com.example.usermanagement.adapter.article;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagement.R;
import com.example.usermanagement.model.User;
import com.example.usermanagement.model.articles.SingleArticle;

public class ArticleVH extends RecyclerView.ViewHolder {
    TextView title,description;
    public ArticleVH(View view) {
        super(view);
        title = view.findViewById(R.id.article_title);
        description = view.findViewById(R.id.article_description);
    }

    public void onBind(SingleArticle article){
        title.setText(article.getTitle());
        description.setText(article.getDescription());
    }
}
