package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.MessagesRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.Group;
import com.cometchat.pro.models.TextMessage;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.Avatar;
import com.cometchat.pro.uikit.BadgeCount;
import com.example.myapplication.R;
import com.example.myapplication.UserChatActivity;
import com.example.myapplication.utils.UserUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import listeners.CometChatCallListener;
import utils.CallUtils;
import utils.Utils;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    List<User> users;
    Context context;
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserAdapter.UserViewHolder(LayoutInflater.from(context).inflate(R.layout.conversation_placeholder, parent, false));
    }
    public UserAdapter(List<User> users, Context context){
        this.users = users;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView username, lastMessage, messageTime;
        private Avatar avatar;
        private ImageView callBtn;
        private RelativeLayout containerLayout;
        private String TAG = "CALL_LISTEN";
        private BadgeCount messageCount;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txt_user_name);
            avatar = itemView.findViewById(R.id.av_user);
            containerLayout = itemView.findViewById(R.id.conversation_holder);
            messageCount = itemView.findViewById(R.id.messageCount);
            lastMessage = itemView.findViewById(R.id.txt_user_message);
            messageTime = itemView.findViewById(R.id.messageTime);
//            callBtn = itemView.findViewById(R.id.call_iv);
        }
        public void bind(User user) {
            username.setText(user.getName());
            avatar.setAvatar(user);

            containerLayout.setOnClickListener(view -> UserChatActivity.start(context, user.getUid(), user.getName(), user.getAvatar()));
            UserUtils.getMissedMessage(user.getUid(), messageCount);
            MessagesRequest messagesRequest = new MessagesRequest.MessagesRequestBuilder().setUID(user.getUid()).setLimit(1).build();
            messagesRequest.fetchPrevious(new CometChat.CallbackListener<List<BaseMessage>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(List<BaseMessage> list) {
                    if(list.size() > 0) {
                        BaseMessage message = list.get(list.size()-1);
                        User sender = message.getSender();
                        String nameSender = sender == CometChat.getLoggedInUser() ? "You" : sender.getName();
                        if(message instanceof  TextMessage) {
                            lastMessage.setText(nameSender + " : " +((TextMessage)message).getText());
                            messageTime.setText(Utils.getLastMessageDate(message.getSentAt()*1000L));
                        }
//                        lastMessage.setText(message.);
                    }
                    Log.d("get_lastmessage", "Text message received successfully: ");
                }
                @Override
                public void onError(CometChatException e) {
                    Log.d("get_lastmessage", "Message fetching failed with exception: " + e.getMessage());
                }
            });

//            callBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "Call clicked");
//                    Call var = new Call(user.getUid(), CometChatConstants.RECEIVER_TYPE_USER, CometChatConstants.CALL_TYPE_AUDIO);
//                    var.setSender(CometChat.getLoggedInUser());
//                    CometChat.initiateCall(var, new CometChat.CallbackListener<Call>() {
//                        @Override
//                        public void onSuccess(Call call) {
//                            Log.e("onSuccess: ", call.toString());
//                            if (var.getReceiverType().equals(CometChatConstants.RECEIVER_TYPE_USER)) {
//                                CallUtils.startCallIntent(v.getContext(), user, CometChatConstants.CALL_TYPE_AUDIO, true, call.getSessionId());
//                            } else
//                                CallUtils.startGroupCallIntent(v.getContext(), ((Group) call.getCallReceiver()), CometChatConstants.CALL_TYPE_AUDIO, true, call.getSessionId());
//                        }
//
//                        @Override
//                        public void onError(CometChatException e) {
//
//                        }
//                    });
//                    CallUtils.initiateCall(v.getContext(), user.getUid(), CometChatConstants.RECEIVER_TYPE_USER, CometChatConstants.CALL_TYPE_VIDEO);
                }
//            });
        }

//    }
}
