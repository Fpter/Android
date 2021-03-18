package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.core.Call;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.Conversation;
import com.cometchat.pro.models.TextMessage;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.Avatar;
import com.cometchat.pro.uikit.BadgeCount;
import com.example.myapplication.R;
import com.example.myapplication.UserChatActivity;
import com.example.myapplication.utils.UserUtils;

import java.util.List;

import utils.Utils;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>  {
    List<Conversation> conversations;
    Context context;
    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationAdapter.ConversationViewHolder(LayoutInflater.from(context).inflate(R.layout.conversation_placeholder, parent, false));

    }
    public ConversationAdapter(List<Conversation> conversations, Context context){
        this.conversations = conversations;
        this.context = context;
    }
    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.bind(conversations.get(position));
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder  {
        private TextView username, lastMessage, messageTime;
        private Avatar avatar;
        private ImageView callBtn;
        private RelativeLayout containerLayout;
        private BadgeCount missedMessageCount;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txt_user_name);
            avatar = itemView.findViewById(R.id.av_user);
            containerLayout = itemView.findViewById(R.id.conversation_holder);
            missedMessageCount = itemView.findViewById(R.id.messageCount);
            lastMessage = itemView.findViewById(R.id.txt_user_message);
            messageTime = itemView.findViewById(R.id.messageTime);

        }
        @SuppressLint("SetTextI18n")
        public void bind(Conversation conversation) {
            User partner = (User)conversation.getConversationWith();
            username.setText(partner.getName());
            avatar.setAvatar(partner.getAvatar());
            BaseMessage baseMessage = conversation.getLastMessage();
            if(baseMessage instanceof  TextMessage) {
                TextMessage message = (TextMessage) baseMessage;
                lastMessage.setText(
                        (Utils.isLoggedInUser(message.getSender()) ? "You" : message.getSender().getName())
                                + ": " + message.getText());

            }else if (baseMessage instanceof Call) {
                Call call = (Call) baseMessage;
                lastMessage.setText(call.getSender().getName() + " call");
            }
            messageTime.setText(Utils.getLastMessageDate(baseMessage.getSentAt()));
            UserUtils.getMissedMessage(partner.getUid(), missedMessageCount);
            containerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     UserChatActivity.start(context, partner.getUid(), partner.getName(), partner.getAvatar());
                }
            });
        }
    }
}
