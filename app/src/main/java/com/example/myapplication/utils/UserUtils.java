package com.example.myapplication.utils;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.MessagesRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.TextMessage;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.BadgeCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class UserUtils {
    static User user_rs = null;
    public static synchronized User getUserById(String UID) {

        CometChat.getUser(UID, new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d("get user", "User details fetched for user: " + user.toString());
                user_rs = user;
            }
            @Override
            public void onError(CometChatException e) {
                Log.d("get user", "User details fetching failed with exception: " + e.getMessage());
            }
        });
        return user_rs;
    }
    public static void updateUserLogined(String name) {
        String apiKey = Constants.AUTH_KEY; // Replace with your API Key.
        User user = CometChat.getLoggedInUser();
        user.setName(name); // Replace with the name of the user

        CometChat.updateUser(user, apiKey, new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d("updateUser", user.toString());
            }

            @Override
            public void onError(CometChatException e) {
                Log.e("updateUser", e.getMessage());
            }
        });
    }
    public static void getMissedMessage(String uid, BadgeCount messageCount) {

        CometChat.getUnreadMessageCountForUser(uid, new CometChat.CallbackListener<HashMap<String, Integer>>() {
            @Override
            public void onSuccess(HashMap<String, Integer> hashMap) {
                if (hashMap.size() > 0) {
                    messageCount.setCount(hashMap.size());
                    messageCount.setVisibility(View.VISIBLE);
//                    messageCount.setCountBackground(Color.RED);
                }else {
                    messageCount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(CometChatException e) {
                // handle error
            }
        });
    }
    public static void markAsRead(BaseMessage message) {
        if(!message.getSender().getUid().equals(CometChat.getLoggedInUser().getUid()))
        CometChat.markAsRead(message.getId(),message.getSender().getUid(), CometChatConstants.RECEIVER_TYPE_USER);
    }

}
