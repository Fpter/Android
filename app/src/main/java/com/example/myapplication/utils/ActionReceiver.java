package com.example.myapplication.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.UserChatActivity;

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context,"recieved",Toast.LENGTH_SHORT).show();

        String uid = intent.getStringExtra("uid");
        String name = intent.getStringExtra("name");
//        String avatar = intent.getStringExtra("avatar");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//        UserChatActivity.start(context.getApplicationContext(), uid, name);
    }


}