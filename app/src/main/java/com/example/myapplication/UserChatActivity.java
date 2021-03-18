package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.MessagesRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.CustomMessage;
import com.cometchat.pro.models.MediaMessage;
import com.cometchat.pro.models.TextMessage;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.Avatar;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.models.CallWrapper;
import com.example.myapplication.models.MessageWrapper;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.UserUtils;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import constant.StringContract;
import listeners.CometChatCallListener;
import screen.CometChatUserDetailScreenActivity;

public class UserChatActivity extends AppCompatActivity {
    private ImageView backBtn;
    private String chatId;
    private String chatName;
    private TextView titleChat;
    private MessagesListAdapter<IMessage> adapter;
    private Avatar ava;
    private User user;
    public UserChatActivity() {
    }

    public static void start(Context context, String chatId, String chatName,String avatar) {
        Intent starter = new Intent(context, UserChatActivity.class);
        starter.putExtra(Constants.CHAT_ID, chatId);
        starter.putExtra(Constants.CHAT_NAME, chatName);
        starter.putExtra(Constants.AVATAR, avatar);
        context.startActivity(starter);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_user_chat);
        Intent intent = getIntent();
        ava = findViewById(R.id.iv_chat_avatar);
        ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CometChat.getUser(chatId, new CometChat.CallbackListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        Log.d("get_user", "User details fetched for user: " + user.toString());
                        Intent intent = new Intent(getApplicationContext(), CometChatUserDetailScreenActivity.class);
                        intent.putExtra(StringContract.IntentStrings.UID, user.getUid());
                        intent.putExtra(StringContract.IntentStrings.NAME, user.getName());
                        intent.putExtra(StringContract.IntentStrings.AVATAR, user.getAvatar());
                        intent.putExtra(StringContract.IntentStrings.STATUS, user.getStatus());
                        intent.putExtra(StringContract.IntentStrings.IS_BLOCKED_BY_ME, user.isBlockedByMe());
                        intent.putExtra(StringContract.IntentStrings.FROM_CALL_LIST, true);
                        startActivity(intent);
                    }
                    @Override
                    public void onError(CometChatException e) {
                        Log.d("get_user", "User details fetching failed with exception: " + e.getMessage());
                    }
                });

            }
        });
        if (intent != null) {
            chatId = intent.getStringExtra(Constants.CHAT_ID);
            chatName = intent.getStringExtra(Constants.CHAT_NAME);
            ava.setAvatar(intent.getStringExtra(Constants.AVATAR));
        }

        titleChat = findViewById(R.id.tv_name);
        titleChat.setText(chatName);
        backBtn = findViewById(R.id.iv_close_message_action);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuChatActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initViews();
        addListener();
        fetchPrevious(chatId, 20);
    }

    private void addListener() {
        CometChat.addMessageListener(chatId, new CometChat.MessageListener() {
            @Override
            public void onTextMessageReceived(TextMessage textMessage) {
                Log.d("receive_message", "Text message received successfully: " + textMessage.toString());
                addMessage(textMessage);
            }
            @Override
            public void onMediaMessageReceived(MediaMessage mediaMessage) {
//                Log.d(TAG, "Media message received successfully: " + mediaMessage.toString());
            }
            @Override
            public void onCustomMessageReceived(CustomMessage customMessage) {
//                Log.d(TAG, "Custom message received successfully: " +customMessage.toString());
            }
        });
    }

    private void initViews() {
        MessageInput inputView = findViewById(R.id.input);
        MessagesList messagesList = findViewById(R.id.messagesList);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                sendMessage(input.toString());
                Log.d("send" , input.toString());
                return true;
            }
        });
        String senderId = CometChat.getLoggedInUser().getUid();
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                Picasso.get().load(url).into(imageView);
            }
        };
        adapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesList.setAdapter(adapter);
    }

    private void sendMessage(String message) {
        TextMessage textMessage = new TextMessage(chatId, message, CometChatConstants.RECEIVER_TYPE_USER);
        CometChat.sendMessage(textMessage, new CometChat.CallbackListener<TextMessage>() {
            @Override
            public void onSuccess(TextMessage textMessage) {
                Log.d("send_message", "Message sent successfully: " + textMessage.toString());
                addMessage(textMessage);
            }
            @Override
            public void onError(CometChatException e) {
                Log.d("send_message", "Message sending failed with exception: " + e.getMessage());
            }
        });

    }

    private void fetchPrevious(String uid, int limit) {
        MessagesRequest messagesRequest = new MessagesRequest.MessagesRequestBuilder().setUID(uid).setLimit(limit).build();
        messagesRequest.fetchPrevious(new CometChat.CallbackListener<List<BaseMessage>>() {
            @Override
            public void onSuccess(List<BaseMessage> list) {
                addMessage(list);
                Log.d("received_message", "Text message received successfully: ");
            }
            @Override
            public void onError(CometChatException e) {
                Log.d("received_message", "Message fetching failed with exception: " + e.getMessage());
            }
        });
    }

    private void addMessage(List<BaseMessage> list) {
        List<IMessage> messageList = new ArrayList<>();
        for(BaseMessage message: list) {

            if(message instanceof TextMessage) {
                messageList.add(new MessageWrapper((TextMessage) message));
                UserUtils.markAsRead(message);
            }else if(message instanceof Call) {
                messageList.add(new CallWrapper((Call)message));
            }
        }
        adapter.addToEnd(messageList, true);
    }
    private void addMessage(TextMessage message) {
        UserUtils.markAsRead(message);
        adapter.addToStart(new MessageWrapper(message), true);
    }

}