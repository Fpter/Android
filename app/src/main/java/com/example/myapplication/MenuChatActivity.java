package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.Group;
import com.cometchat.pro.models.TextMessage;
import com.cometchat.pro.models.User;
import com.example.myapplication.fragments.CallListFragment;
import com.example.myapplication.fragments.GroupFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.MessageFragment;
import com.example.myapplication.fragments.MoreInfoFragment;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import screen.CometChatCallActivity;
import screen.CometChatStartCallActivity;
import utils.CallUtils;

public class MenuChatActivity extends AppCompatActivity {
    private TextView titleBar;
    private ImageView backBtn;
    public static void start(Context context) {
        Intent starter = new Intent(context, MenuChatActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_menu_chat);
        titleBar = findViewById(R.id.titleChat);
        titleBar.setText("Home");
        backBtn = findViewById(R.id.backIcon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        receiveCall();
        ImageView backBtn = findViewById(R.id.backIcon);
        backBtn.setVisibility(View.GONE);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFrag = new HomeFragment();
                        titleBar.setText("Home");
                        break;
                    case R.id.nav_group:
                        selectedFrag = new GroupFragment();
                        titleBar.setText("Group");
                        break;
                    case R.id.nav_message:
                        selectedFrag = new MessageFragment();
                        titleBar.setText("Message");
                        break;
                    case R.id.nav_info:
                        selectedFrag = new MoreInfoFragment();
                        titleBar.setText("Infomations");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();
                return true;
            }
        });
        getMessageRealTime();
    }
    private void getMessageRealTime() {
        CometChat.addMessageListener(Constants.LISTEN_MESSAGE_RECEIVED, new CometChat.MessageListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextMessageReceived(TextMessage message) {
                String title = message.getSender().getName();
                if(message.getReceiverType().equals(CometChatConstants.RECEIVER_TYPE_GROUP)){
                    title = title + " to " + message.getConversationId();
                }

                new Notification().showNotification(message, getApplicationContext(), Constants.LISTEN_MESSAGE_RECEIVED, Constants.NOTIFY_ID);
                Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("homeFragment");
                if(homeFragment != null && homeFragment.isVisible())
                {
                    View view = homeFragment.getView();
                }
            }

        });
    }
    private void receiveCall() {
        Context context = getApplicationContext();
        CometChat.addCallListener(Constants.RECEIVE_CALL, new CometChat.CallListener() {
            @Override
            public void onIncomingCallReceived(Call call) {
                if (CometChat.getActiveCall()==null) {
                    if (call.getReceiverType().equals(CometChatConstants.RECEIVER_TYPE_USER)) {
                        CallUtils.startCallIntent(getApplicationContext(), (User) call.getCallInitiator(), call.getType(),
                                false, call.getSessionId());
                    } else {
                        CallUtils.startGroupCallIntent(context, (Group) call.getReceiver(), call.getType(),
                                false, call.getSessionId());
                    }
                } else {
                    CometChat.rejectCall(call.getSessionId(), CometChatConstants.CALL_STATUS_BUSY, new CometChat.CallbackListener<Call>() {
                        @Override
                        public void onSuccess(Call call) {
                            Toast.makeText(context,call.getSender().getName()+" tried to call you",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(CometChatException e) {
                            Toast.makeText(context,"Error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onOutgoingCallAccepted(Call call) {
                if(CometChatStartCallActivity.activity==null) {
                    if (CometChatCallActivity.callActivity != null) {
                        CometChatCallActivity.cometChatAudioHelper.stop(false);
                        CallUtils.startCall(CometChatCallActivity.callActivity, call);
                    }
                } else {
                    CometChatStartCallActivity.activity.finish();
                    if (CometChatCallActivity.callActivity != null) {
                        CometChatCallActivity.cometChatAudioHelper.stop(false);
                        CallUtils.startCall(CometChatCallActivity.callActivity, call);
                    }
                }
            }

            @Override
            public void onOutgoingCallRejected(Call call) {
                if (CometChatCallActivity.callActivity!=null)
                    CometChatCallActivity.callActivity.finish();
            }

            @Override
            public void onIncomingCallCancelled(Call call){
                if (CometChatCallActivity.callActivity!=null)
                    CometChatCallActivity.callActivity.finish();
            }
        });
    }

}