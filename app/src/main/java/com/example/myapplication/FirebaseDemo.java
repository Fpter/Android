package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.SendNotification.NotificationData;
import com.example.myapplication.SendNotification.PushNotification;
import com.example.myapplication.SendNotification.RetrofitInstance;
import com.example.myapplication.utils.Item;
import com.example.myapplication.utils.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class FirebaseDemo extends AppCompatActivity {
    String TAG = "FirebaseDemo";
    String TOPIC = "/topics/myTopic";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_demo);
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Notification().showNotification("test", "saker", v.getContext(), "123", 101);
            }
        });
//        EditText txtTitle = findViewById(R.id.et_title);
//        EditText txtMessage = findViewById(R.id.et_message);
//        EditText txtToken = findViewById(R.id.et_token);
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = txtTitle.getText().toString();
//                String message = txtMessage.getText().toString();
//                if (!title.isEmpty() && !message.isEmpty()) {
//
//                    sendNotification(new PushNotification(new NotificationData(title,message),TOPIC));
//                }
//            }
//        });
    }
//    private void sendNotification(PushNotification notification) {
//
//        Response<ResponseBody> response = new RetrofitInstance().getApi().postNotification(notification);
//        if(response.isSuccessful()) {
//            Log.d(TAG, "Response: " + new Gson().toJson(response));
//        }else {
//            Log.e(TAG, "Response " + new Gson().toJson(response));
//        }
//    }
}