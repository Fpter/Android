package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.example.myapplication.utils.Constants;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Constructor;
import java.util.Objects;

public class CreateUserActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, CreateUserActivity.class);
        context.startActivity(starter);
    }
    private TextInputEditText username, fullname;
    private Button createBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        try {
            Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        }catch (Exception e) {}
        setContentView(R.layout.activity_create_user);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiKey = Constants.AUTH_KEY; // Replace with your API Key.
                User user = new User();
                user.setUid(username.getText().toString()); // Replace with your uid for the user to be created.
                user.setName(fullname.getText().toString()); // Replace with the name of the user

                CometChat.createUser(user, apiKey, new CometChat.CallbackListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        Log.d("createUser", user.toString());
                        Toast.makeText(v.getContext(), "Success", Toast.LENGTH_LONG);
                        MainActivity.start(v.getContext());
                    }

                    @Override
                    public void onError(CometChatException e) {
                        Toast.makeText(v.getContext(), "fail", Toast.LENGTH_LONG);
                        Log.e("createUser", e.getMessage());
                    }
                });
            }
        });
    }
}