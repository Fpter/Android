package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.MessagesRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.CustomMessage;
import com.cometchat.pro.models.MediaMessage;
import com.cometchat.pro.models.TextMessage;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.Avatar;
import com.example.myapplication.models.MessageWrapper;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.UserUtils;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private String groupId;
    private String groupName;
    private TextView titleChat;
    private MessagesListAdapter<IMessage> adapter;
    private ImageView backBtn;
    private Avatar ava;
    private User user;
    public static void start(Context context, String guid, String groupName, String icon) {
        Intent starter = new Intent(context, ChatActivity.class);
        starter.putExtra(Constants.GROUP_ID, guid);
        starter.putExtra(Constants.GROUP_NAME, groupName);
        starter.putExtra(Constants.GROUP_ICON, icon);
        context.startActivity(starter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title

        try {
            Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        }catch (Exception e) {}
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        ava = findViewById(R.id.iv_chat_avatar);
        if(intent != null) {
            groupId = intent.getStringExtra(Constants.GROUP_ID);
            groupName = intent.getStringExtra(Constants.GROUP_NAME);
            ava.setAvatar(intent.getStringExtra(Constants.GROUP_ICON));

        }
        titleChat = findViewById(R.id.tv_name);
        titleChat.setText(groupName);
        backBtn = findViewById(R.id.iv_close_message_action);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ava = findViewById(R.id.iv_chat_avatar);
        user = UserUtils.getUserById(groupId);

        initViews();
        addListener();
        fetchPrevious();
    }

    private void fetchPrevious() {
        MessagesRequest messagesRequest = new MessagesRequest.MessagesRequestBuilder().setLimit(10).setGUID(groupId).build();

        messagesRequest.fetchPrevious(new CometChat.CallbackListener<List<BaseMessage>>() {
            @Override
            public void onSuccess(List <BaseMessage> list) {
                for (BaseMessage message: list) {
                    if (message instanceof TextMessage) {
                        Log.d("message_previous", "Text message received successfully: " + list.size() +
                                ((TextMessage) message).toString());
                        addMessages(list);
                    } else if (message instanceof MediaMessage) {
                        Log.d("message_previous", "Media message received successfully: " +
                                ((MediaMessage) message).toString());
                    }
                }
            }
            @Override
            public void onError(CometChatException e) {
                Log.d("", "Message fetching failed with exception: " + e.getMessage());
            }
        });
    }

    private void addMessages(List<BaseMessage> list) {
        List<IMessage> messageList = new ArrayList<>();
        for(BaseMessage message : list) {
            if(message instanceof TextMessage) {
                messageList.add(new MessageWrapper((TextMessage) message));
            }
        }
        adapter.addToEnd(messageList, true);
    }

    private void addListener() {
        String listenerID = "listener 1";
        CometChat.addMessageListener(listenerID, new CometChat.MessageListener() {
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
        try {
            ava.setAvatar(UserUtils.getUserById(groupId));
        }catch (Exception e) {

        }
        MessageInput inputView = findViewById(R.id.input);
        MessagesList messagesList = findViewById(R.id.messagesList);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                sendMessage(input.toString());
                Log.d("send", input.toString());
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
        TextMessage textMessage = new TextMessage(groupId, message, CometChatConstants.RECEIVER_TYPE_GROUP);

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

    private void addMessage(TextMessage message) {
        adapter.addToStart(new MessageWrapper(message), true);
    }
}