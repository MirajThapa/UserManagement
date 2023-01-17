package com.example.usermanagement.ui.files;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usermanagement.R;
import com.example.usermanagement.databinding.ActivityRegisterUserBinding;
import com.example.usermanagement.databinding.ActivitySelectFileBinding;
import com.example.usermanagement.model.FileServerResponse;
import com.example.usermanagement.network.IAddFiles;
import com.example.usermanagement.network.RetrofitClient;
import com.example.usermanagement.utils.AppLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectFileActivity extends AppCompatActivity {

    ActivitySelectFileBinding activitySelectFileBinding;

    Button BSelectImage;

    // One Preview Image
    ImageView IVPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySelectFileBinding = ActivitySelectFileBinding.inflate(getLayoutInflater());
        setContentView(activitySelectFileBinding.getRoot());

        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        activitySelectFileBinding.IVPreviewImage.setImageURI(uri);
                        AppLog.showLog(String.valueOf(uri));

                        activitySelectFileBinding.UploadImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                uploadFile(uri);
                            }
                        });
                    }
                });

        activitySelectFileBinding.BSelectImage.setOnClickListener(view -> mGetContent.launch("image/*"));

    }

    private void uploadFile(Uri imageUri) {

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file",String.valueOf(System.currentTimeMillis()+".png"), getRequestBodyForFolderImages(this,imageUri));
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        IAddFiles iAddFiles = RetrofitClient.getClient().create(IAddFiles.class);

        iAddFiles.uploadFile(fileToUpload).enqueue(new Callback<FileServerResponse>() {
            @Override
            public void onResponse(Call<FileServerResponse> call, Response<FileServerResponse> response) {
                FileServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), "uploaded",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Not uploaded",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    AppLog.showLog("Error");
                }
            }

            @Override
            public void onFailure(Call<FileServerResponse> call, Throwable t) {
                AppLog.showLog("Error"+t.getMessage());
                AppLog.showLog("Error"+t.getLocalizedMessage());
            }
        });
    }


    public static RequestBody getRequestBodyForFolderImages(Context context, Uri uri) {
        File file = null;
        try {
            file = getFile(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // if you need to compressed image before sending (optional)
        RequestBody imageReqBody;
        if (file != null) {
            imageReqBody = RequestBody.create( MediaType.parse("image/*"),file);
        } else {
            assert file != null;
            imageReqBody = RequestBody.create( MediaType.parse("image/*"),file);
        }
        return imageReqBody;
    }

    public static File getFile(Context context, Uri uri) throws IOException {
        File destinationFilename;
        try {
            destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        } catch (AssertionError e) {
            destinationFilename = new File(uri.getPath());
        }
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }



    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }


}