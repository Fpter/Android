package com.example.myapplication.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.models.TextMessage;
import com.cometchat.pro.models.User;
import com.example.myapplication.MenuChatActivity;
import com.example.myapplication.R;
import com.example.myapplication.UserChatActivity;

public class Notification {
    public Notification() {
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification(TextMessage message, Context context, String channelID, int notifyID) {
        Intent notifyIntent = new Intent(context, UserChatActivity.class);
// Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notifyIntent.putExtra(Constants.CHAT_ID, message.getSender().getUid());
        notifyIntent.putExtra(Constants.CHAT_NAME, message.getSender().getName());
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle(message.getSender().getName())
                .setContentText(message.getText())
                .setChannelId(channelID)
                .addAction(R.drawable.heart_reaction, Constants.VIEW_NOTIFICATION, notifyPendingIntent);

        NotificationChannel channel = new NotificationChannel(channelID, "My notification channel", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(notifyID, builder.build());

        Log.d("notification", "showed");

    }
}
