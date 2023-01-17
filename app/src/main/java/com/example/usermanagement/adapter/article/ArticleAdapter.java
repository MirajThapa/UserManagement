package com.example.usermanagement.adapter.article;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagement.R;
import com.example.usermanagement.model.articles.SingleArticle;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter{

    List<SingleArticle> singleArticleList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArticleVH articleVH = (ArticleVH) holder;
        articleVH.onBind(singleArticleList.get(position));
    }

    @Override
    public int getItemCount() {
        if(singleArticleList != null){
            return singleArticleList.size();
        }
        else {
            return 0;
        }
    }

    public void updateArticle(List<SingleArticle> singleArticleList){
        this.singleArticleList = singleArticleList;
        notifyDataSetChanged();
    }
}
