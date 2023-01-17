package com.example.usermanagement.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.usermanagement.IUserArticles;
import com.example.usermanagement.adapter.UserAdapter;
import com.example.usermanagement.databinding.ActivityUserListBinding;
import com.example.usermanagement.model.User;
import com.example.usermanagement.ui.articles.ArticleActivity;

import java.util.List;

public class UserListActivity extends AppCompatActivity implements IUserArticles {
    UserViewModel userViewModel;
    UserAdapter userAdapter;
    ActivityUserListBinding activityUserListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserListBinding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(activityUserListBinding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers();

        setRecyclerView();

        userViewModel.getUserLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.updateUser(users);
            }
        });

    }

    public void setRecyclerView(){
        userAdapter = new UserAdapter(this);
        activityUserListBinding.userRv.setLayoutManager(new LinearLayoutManager(this));
        activityUserListBinding.userRv.setAdapter(userAdapter);
    }

    @Override
    public void specificUserArticles(String id) {
        Intent intent = new Intent(UserListActivity.this, ArticleActivity.class);
        intent.putExtra("ID",id);
        startActivity(intent);
    }
}