package com.example.usermanagement.model;

import com.google.gson.annotations.SerializedName;

public class FileServerResponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;
    String getMessage() {
        return message;
    }
    public boolean getSuccess() {
        return success;
    }
}
