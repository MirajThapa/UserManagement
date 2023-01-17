package com.example.usermanagement.ui.register;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.usermanagement.model.AllUsers;
import com.example.usermanagement.model.FileServerResponse;
import com.example.usermanagement.model.User;
import com.example.usermanagement.network.IAddFiles;
import com.example.usermanagement.network.IUserApi;
import com.example.usermanagement.network.RetrofitClient;
import com.example.usermanagement.utils.AppLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends AndroidViewModel {

    private Application application;
    public RegisterViewModel(@NonNull Application application) {
        super(application);
         this.application = application;
    }

    public void registerUser(User user){

        IUserApi iUserApi = RetrofitClient.getClient().create(IUserApi.class);

        iUserApi.registerUser(user.getEmail(),user.getUsername(),user.getPassword(), user.getGender(), user.getPhoto());

        iUserApi.registerUser(user.getEmail(),user.getUsername(),user.getPassword(), user.getGender(), user.getPhoto()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Log.i("bcbc", " success");
                    }
                }
                else {
                    Log.i("bcbc", "not success");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    public void uploadFile(Uri imageUri,Context context) {

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file",String.valueOf(System.currentTimeMillis()+".png"), getRequestBodyForFolderImages(context,imageUri));
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        IAddFiles iAddFiles = RetrofitClient.getClient().create(IAddFiles.class);

        iAddFiles.uploadFile(fileToUpload).enqueue(new Callback<FileServerResponse>() {
            @Override
            public void onResponse(Call<FileServerResponse> call, Response<FileServerResponse> response) {
                FileServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {

                    } else {

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
