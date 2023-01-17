package com.example.usermanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagement.IUserArticles;
import com.example.usermanagement.R;
import com.example.usermanagement.model.AllUsers;
import com.example.usermanagement.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter{
    List<User> users;

    IUserArticles iUserArticles;

    public UserAdapter( IUserArticles iUserArticles) {
        this.iUserArticles = iUserArticles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user,parent,false);
        return new SingleUser(view,iUserArticles);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SingleUser singleUser = (SingleUser) holder;
        singleUser.onBind(users.get(position));

    }

    @Override
    public int getItemCount() {
        if(users != null){
            return users.size();
        }
        else {
            return 0;
        }
    }

    public void updateUser(List<User> user) {
        this.users = user;
        notifyDataSetChanged();
    }
}
