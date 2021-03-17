package com.example.myapplication.SendNotification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessageService extends FirebaseMessagingService {
    private String title, message;
    public String TAG = "mylog";
    String CHANNEL_ID = "my_notification";

    public MyFirebaseMessageService() {
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "refreshed token: " + token);

    }
}