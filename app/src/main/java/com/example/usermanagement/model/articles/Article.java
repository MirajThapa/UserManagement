package com.example.usermanagement.model.articles;

import com.example.usermanagement.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("articles")
    @Expose
    private List<SingleArticle> articles = null;
    @SerializedName("error")
    @Expose
    private String error;

    public List<SingleArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<SingleArticle> articles) {
        this.articles = articles;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
