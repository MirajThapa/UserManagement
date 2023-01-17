package com.example.usermanagement.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagement.IUserArticles;
import com.example.usermanagement.R;
import com.example.usermanagement.model.User;

public class SingleUser extends RecyclerView.ViewHolder {
    TextView name,email,gender;
    IUserArticles itemClickListener;

    public SingleUser(View view,IUserArticles itemClickListener) {
        super(view);
        name = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.user_email);
        gender = view.findViewById(R.id.user_gender);
        this.itemClickListener = itemClickListener;
    }

    public void onBind(User user){
        name.setText(user.getUsername());
        email.setText(user.getEmail());
        gender.setText(user.getGender());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null){
                    Log.i("sss", user.getId());
                    itemClickListener.specificUserArticles(user.getId());
                }
                else {
                    Log.i("sss", user.getId());
                }
            }
        });
    }
}
