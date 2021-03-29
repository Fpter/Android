package com.example.myapplication.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.Avatar;
import com.example.myapplication.R;
import com.example.myapplication.utils.UserUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import utils.CallUtils;
import utils.Utils;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallViewHolder>{
    List<BaseMessage> calls;
    Context context;
    public CallAdapter (List<BaseMessage> calls, Context context) {
        this.calls = calls;
        this.context = context;
    }
    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CallAdapter.CallViewHolder(LayoutInflater.from(context).inflate(R.layout.call_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CallViewHolder holder, int position) {
        holder.bind(calls.get(position));
//        holder.call_message.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_call_incoming_24dp), null, null, null);
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }
    public void getCall(View v, User user) {
        new MaterialAlertDialogBuilder(context).setTitle("Call coming").setMessage("Choose type call")
                .setNegativeButton("Audio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call var = new Call(user.getUid(), CometChatConstants.RECEIVER_TYPE_USER, CometChatConstants.CALL_TYPE_AUDIO);
                        CometChat.initiateCall(var, new CometChat.CallbackListener<Call>() {
                            @Override
                            public void onSuccess(Call call) {
                                Log.e("onSuccess: ", call.toString());
                                if (var.getReceiverType().equals(CometChatConstants.RECEIVER_TYPE_USER)) {
                                    CallUtils.startCallIntent(v.getContext(), user, CometChatConstants.CALL_TYPE_AUDIO, true, call.getSessionId());
                                }
                            }

                            @Override
                            public void onError(CometChatException e) {

                            }
                        });
                    }
                }).setPositiveButton("Video", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CallUtils.initiateCall(v.getContext(), user.getUid(), CometChatConstants.RECEIVER_TYPE_USER, CometChatConstants.CALL_TYPE_VIDEO);

            }
        })
                // Add customization options here
                .show();
    }
    public class CallViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout callView;
        private TextView call_sender_name, calltime_tv, call_message;
        private Avatar call_sender_avatar;
        private ImageView call_iv;

        public CallViewHolder(@NonNull View itemView) {
            super(itemView);
            callView = itemView.findViewById(R.id.callView);
            call_sender_name = itemView.findViewById(R.id.call_sender_name);
            call_sender_avatar = itemView.findViewById(R.id.call_sender_avatar);
            calltime_tv = itemView.findViewById(R.id.calltime_tv);
            call_message = itemView.findViewById(R.id.call_message);
            call_iv = itemView.findViewById(R.id.call_iv);
        }
        public void bind(BaseMessage call) {
            Call mcall = (Call) call;
            String callMessageText="";
            User partner = Utils.isLoggedInUser(call.getSender()) ? (User) call.getReceiver() : call.getSender();
            call_sender_name.setText(partner.getName());
            call_sender_avatar.setAvatar(partner);
            calltime_tv.setText(Utils.getLastMessageDate(call.getSentAt()));
            call_message.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_call_incoming_24dp), null, null, null);
            boolean isIncoming=false, isVideo=false, isMissed=false;
            if (mcall.getSender().getUid().equals(CometChat.getLoggedInUser())) {
                if(mcall.getCallStatus().equals(CometChatConstants.CALL_STATUS_UNANSWERED)) {
                    callMessageText = context.getResources().getString(com.cometchat.pro.uikit.R.string.missed_call);
                    isMissed = true;
                } else if(mcall.getCallStatus().equals(CometChatConstants.CALL_STATUS_REJECTED)) {
                    callMessageText = context.getResources().getString(com.cometchat.pro.uikit.R.string.rejected_call);
                } else
                    callMessageText = context.getResources().getString(com.cometchat.pro.uikit.R.string.outgoing);
                isIncoming = false;
            } else {
                if(mcall.getCallStatus().equals(CometChatConstants.CALL_STATUS_UNANSWERED)) {
                    callMessageText = context.getResources().getString(com.cometchat.pro.uikit.R.string.missed_call);
                    isMissed = true;
                } else if(mcall.getCallStatus().equals(CometChatConstants.CALL_STATUS_REJECTED)) {
                    callMessageText = context.getResources().getString(com.cometchat.pro.uikit.R.string.rejected_call);
                } else
                    callMessageText = context.getResources().getString(com.cometchat.pro.uikit.R.string.incoming);
                isIncoming = true;
            }
            if(call.getType().equals(CometChatConstants.CALL_TYPE_VIDEO))
            {
                callMessageText = callMessageText+" "+context.getResources().getString(com.cometchat.pro.uikit.R.string.video_call);
                isVideo = true;
            }
            else
            {
                callMessageText = callMessageText+" "+context.getResources().getString(com.cometchat.pro.uikit.R.string.audio_call);
                isVideo = false;
            }

            if (isVideo)
            {
                call_message.setCompoundDrawablesWithIntrinsicBounds(com.cometchat.pro.uikit.R.drawable.ic_videocam_24dp,0,0,0);
            }
            else
            {

                if (isIncoming && isMissed) {
                    call_message.setCompoundDrawablesWithIntrinsicBounds(com.cometchat.pro.uikit.R.drawable.ic_call_missed_incoming_24dp,0,0,0);
                } else if(isIncoming && !isMissed) {
                    call_message.setCompoundDrawablesWithIntrinsicBounds(com.cometchat.pro.uikit.R.drawable.ic_call_incoming_24dp,0,0,0);
                } else if (!isIncoming && isMissed) {
                    call_message.setCompoundDrawablesWithIntrinsicBounds(com.cometchat.pro.uikit.R.drawable.ic_call_missed_outgoing_24dp,0,0,0);
                } else {
                    call_message.setCompoundDrawablesWithIntrinsicBounds(com.cometchat.pro.uikit.R.drawable.ic_call_outgoing_24dp,0,0,0);
                }
            }
            call_message.setText(callMessageText);
            call_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = Utils.isLoggedInUser(call.getSender()) ? (User) ((Call) call).getCallReceiver() : call.getSender();
                    getCall(v, user);
                }
            });
        }
    }
}
