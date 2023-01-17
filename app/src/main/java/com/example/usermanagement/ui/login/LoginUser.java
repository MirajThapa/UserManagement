package com.example.usermanagement.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.usermanagement.R;
import com.example.usermanagement.databinding.ActivityLoginUserBinding;
import com.example.usermanagement.model.LoginResponse;
import com.example.usermanagement.ui.files.SelectFileActivity;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{
    ActivityLoginUserBinding activityLoginUserBinding;
    String mail,password;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginUserBinding = ActivityLoginUserBinding.inflate(getLayoutInflater());
        setContentView(activityLoginUserBinding.getRoot());

        activityLoginUserBinding.loginButton.setOnClickListener(this);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.userMutableLiveData.observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                launchActivity();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login_button:
                mail = activityLoginUserBinding.email.getText().toString();
                password = activityLoginUserBinding.password.getText().toString();
                loginViewModel.checkLogin(mail,password);
                break;
        }
    }

    private void launchActivity() {
        Intent intent = new Intent(LoginUser.this, SelectFileActivity.class);
        startActivity(intent);
    }
}