package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.UsersRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.Conversation;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.CometChatUserList;
import com.cometchat.pro.uikit.Settings.UISettings;
import com.example.myapplication.adapters.ConversationAdapter;
import com.example.myapplication.adapters.UserAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import adapter.TabAdapter;
import screen.call.AllCall;
import screen.call.MissedCall;

public class NewCallList extends AppCompatActivity {
    private UsersRequest userRequest;
    private RecyclerView rv_user_list;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout list_user;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.fragment_user_list_screen);
        rv_user_list = findViewById(R.id.rv_user_list);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        list_user =findViewById(R.id.list_user);
        searchBar = findViewById(R.id.search_bar);

        fetchUsers();
        rv_user_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(recyclerView.canScrollVertically(1)) {
                    fetchUsers();
                }
            }
        });
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchUser(s.toString());
            }
        });
    }
    private void updateUI(List<User> users) {
        rv_user_list.setLayoutManager(new LinearLayoutManager(this));
        UserAdapter userAdapter = new UserAdapter(users, this);
        rv_user_list.setAdapter(userAdapter);
    }
    private void fetchUsers() {
        if(userRequest == null) {
            Log.e("CometChatUserCallList", "userrequest null");
            userRequest = new UsersRequest.UsersRequestBuilder().setLimit(30).build();
        }
        userRequest.fetchNext(new CometChat.CallbackListener<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                Log.d("onfetchSuccess", "user list : " + users.size());
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                list_user.setVisibility(View.GONE);
                updateUI(users);
            }

            @Override
            public void onError(CometChatException e) {
                Log.d("onfetch fail", e.getMessage());
            }
        });
    }
    private void searchUser(String s) {
        UsersRequest usersRequest = new UsersRequest.UsersRequestBuilder().setSearchKeyword(s).setLimit(100).build();
        usersRequest.fetchNext(new CometChat.CallbackListener<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                updateUI(users);
            }

            @Override
            public void onError(CometChatException e) {
            }
        });
    }
}