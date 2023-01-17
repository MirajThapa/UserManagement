package com.example.usermanagement.ui.register;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.usermanagement.R;
import com.example.usermanagement.databinding.ActivityRegisterUserBinding;
import com.example.usermanagement.model.User;
import com.example.usermanagement.ui.login.LoginViewModel;
import com.example.usermanagement.utils.AppLog;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityRegisterUserBinding activityRegisterUserBinding;
    RegisterViewModel registerViewModel;

    String email,name,gender,password,photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterUserBinding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterUserBinding.getRoot());

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        activityRegisterUserBinding.registerUserData.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.register_user_data:
                userAdded();
                uploadImage();
                break;
        }
    }

    private void uploadImage(){
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        Toast.makeText(RegisterUserActivity.this, "upload image", Toast.LENGTH_SHORT).show();
                        registerViewModel.uploadFile(uri,RegisterUserActivity.this);
                    }
                });

        activityRegisterUserBinding.addProfilePicture.setOnClickListener(view -> mGetContent.launch("image/*"));

    }

    private void userAdded() {
        email = activityRegisterUserBinding.registerEmail.getText().toString();
        name = activityRegisterUserBinding.registerUsername.getText().toString();
        gender = activityRegisterUserBinding.registerGender.getText().toString();
        password = activityRegisterUserBinding.registerPassword.getText().toString();


        User user = new User();
        user.setUsername(name);
        user.setGender(gender);
        user.setEmail(email);
        user.setPassword(password);
//        user.setPhoto();
        registerViewModel.registerUser(user);
    }
}