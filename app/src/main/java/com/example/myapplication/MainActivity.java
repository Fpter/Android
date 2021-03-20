package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.UserUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import utils.Utils;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextInputLayout inputLayout;
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_main);
        initComet();
        initViews();
    }

    private void initViews() {
        TextInputEditText userId = findViewById(R.id.userID);
        Button loginBtn = findViewById(R.id.submitBtn);
        Button signUpBtn = findViewById(R.id.signupbtn);
        progressBar = findViewById(R.id.loginProgress);
        inputLayout = findViewById(R.id.inputUID);
        userId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(userId.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Fill username field", Toast.LENGTH_LONG).show();
                    }else{
                        progressBar.setVisibility(View.VISIBLE);
                        inputLayout.setEndIconVisible(false);
                        login(userId.getText().toString());
                    }
                }
                return true;
            }
        });
        inputLayout.setEndIconOnClickListener(view -> {
            if (userId.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Fill Username field", Toast.LENGTH_LONG).show();
            }
            else {
                findViewById(R.id.loginProgress).setVisibility(View.VISIBLE);
                inputLayout.setEndIconVisible(false);
                login(userId.getText().toString());
            }

        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUserActivity.start(v.getContext());
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                String UID = userId.getText().toString(); // Replace with the UID of the user to login
                if (CometChat.getLoggedInUser() == null) {
                    CometChat.login(UID, Constants.AUTH_KEY, new CometChat.CallbackListener<User>() {

                        @Override
                        public void onSuccess(User user) {
                            Snackbar.make(v, "Login with " + user.getName(), Snackbar.LENGTH_LONG).show();
                            Log.d("login_comet", "Login Successful : " + user.toString());
                            redirectToGroupList();
                        }

                        @Override
                        public void onError(CometChatException e) {
                            Toast.makeText(MainActivity.this, "Login failed with exception: " + e.getMessage(), Toast.LENGTH_SHORT);
                            Log.d("login_comet", "Login failed with exception: " + e.getMessage());
                        }
                    });
                } else {
                    Log.d("login_comet", "User already logged in: ");
                    // User already logged in
                    redirectToGroupList();
                }
            }
        });
    }

    private void redirectToGroupList() {
        MenuChatActivity.start(this);
    }
    private void logout() {
        CometChat.logout(new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d("logout", "Logout completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
                Log.d("logout", "Logout failed with exception: " + e.getMessage());
            }
        });
    }
    private void initComet() {
        String appID = Constants.APP_ID; // Replace with your App ID
        String region = "US"; // Replace with your App Region ("eu" or "us")

        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region).build();

        CometChat.init(this, appID,appSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d("init_comet", "Initialization completed successfully");

            }

            @Override
            public void onError(CometChatException e) {
                Log.d("init_comet", "Initialization failed with exception: " + e.getMessage());
            }
        });
        logout();
    }
    private void login(String username) {
        logout();
        if (CometChat.getLoggedInUser() == null) {
            CometChat.login(username, Constants.AUTH_KEY, new CometChat.CallbackListener<User>() {

                @Override
                public void onSuccess(User user) {

                    Log.d("login_comet", "Login Successful : " + user.toString());
                    redirectToGroupList();
                }

                @Override
                public void onError(CometChatException e) {
                    inputLayout.setEndIconVisible(true);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Login failed with exception: " + e.getMessage(), Toast.LENGTH_SHORT);
                    Log.d("login_comet", "Login failed with exception: " + e.getMessage());
                }
            });
        } else {
            Log.d("login_comet", "User already logged in: ");
            // User already logged in
            redirectToGroupList();
        }
    }
}