package com.example.myapplication.SendNotification;

import com.example.myapplication.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationAPI {
    @Headers({
            "Authoriztation: key=" + Constants.SERVER_KEY,
            "Content-Type: " + Constants.CONTENT_TYPE
    })
    @POST("fcm/send")
    Response<ResponseBody> postNotification(@Body PushNotification notification);
}
