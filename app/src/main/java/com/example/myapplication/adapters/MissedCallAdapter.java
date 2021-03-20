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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import utils.CallUtils;
import utils.Utils;

public class MissedCallAdapter extends RecyclerView.Adapter<MissedCallAdapter.MissedCallViewHolder>{
    @NonNull
    @Override
    public MissedCallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MissedCallAdapter.MissedCallViewHolder(LayoutInflater.from(context).inflate(R.layout.call_list_row, parent, false));
    }
    private List<BaseMessage> missedCalls;
    Context context;
    public MissedCallAdapter (List<BaseMessage> missedCalls, Context context) {
        this.missedCalls = missedCalls;
        this.context = context;
    }
    @Override
    public void onBindViewHolder(@NonNull MissedCallViewHolder holder, int position) {
        holder.bind(missedCalls.get(position));
    }

    @Override
    public int getItemCount() {
        return missedCalls.size();
    }

    public class MissedCallViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout callView;
        private TextView call_sender_name, calltime_tv, call_message;
        private Avatar call_sender_avatar;
        private ImageView call_iv;
        public MissedCallViewHolder(@NonNull View itemView) {
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
            String type_call = "Missed Audio Call";
            if(call.getType() == CometChatConstants.CALL_TYPE_VIDEO) {
                type_call = "Missed Video Call";
            }
            User partner = Utils.isLoggedInUser(mcall.getSender()) ? (User) call.getReceiver() : call.getSender();
            call_sender_name.setText(partner.getName());
            call_sender_avatar.setAvatar(partner);
            calltime_tv.setText(Utils.getLastMessageDate(mcall.getSentAt()));
            call_message.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_call_missed_incoming_24dp), null, null, null);
            call_message.setText(type_call);
            call_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = Utils.isLoggedInUser(mcall.getSender()) ? (User) ((Call) call).getCallReceiver() : call.getSender();
                    getCall(v, user);
                }
            });
        }

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
}
