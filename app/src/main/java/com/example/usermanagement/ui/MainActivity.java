package com.example.usermanagement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.usermanagement.R;
import com.example.usermanagement.adapter.UserAdapter;
import com.example.usermanagement.databinding.ActivityMainBinding;
import com.example.usermanagement.ui.login.LoginUser;
import com.example.usermanagement.ui.register.RegisterUserActivity;
import com.example.usermanagement.ui.user.UserViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    UserViewModel userViewModel;
    UserAdapter userAdapter;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        userViewModel.getAllUsers();
//
//        setRecyclerView();
        activityMainBinding.registerUser.setOnClickListener(this);
        activityMainBinding.loginUser.setOnClickListener(this);
//
//        userViewModel.getUserLiveData().observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//                userAdapter.updateUser(users);
//            }
//        });
    }

//    public void setRecyclerView(){
//        userAdapter = new UserAdapter();
//        activityMainBinding.userRv.setLayoutManager(new LinearLayoutManager(this));
//        activityMainBinding.userRv.setAdapter(userAdapter);
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.register_user:
                Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            break;
            case R.id.login_user:
                Intent intent1 = new Intent(MainActivity.this, LoginUser.class);
                startActivity(intent1);
                break;
        }
    }
}