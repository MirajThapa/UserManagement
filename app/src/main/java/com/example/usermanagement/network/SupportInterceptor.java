package com.example.usermanagement.network;

import com.example.usermanagement.preferences.PreferenceHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SupportInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        String accessToken = "";
        accessToken = PreferenceHelper.getLoginToken();

        request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "max-age=60")
                .addHeader("X-Anydone-Product", "ANYDONE_INBOX")
                .addHeader("Authorization", accessToken)
                .build();


        Response response = chain.proceed(request);
        if (response.code() == 401) {
            //Todo do unauthorized to access work
        }
        return response;
    }
}