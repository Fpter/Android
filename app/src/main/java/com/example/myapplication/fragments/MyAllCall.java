package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.MessagesRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.Conversation;
import com.cometchat.pro.models.Group;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.CometChatCallList;
import com.example.myapplication.R;
import com.example.myapplication.adapters.CallAdapter;
import com.example.myapplication.adapters.ConversationAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import constant.StringContract;
import listeners.OnItemClickListener;
import screen.CometChatGroupDetailScreenActivity;
import screen.CometChatUserDetailScreenActivity;
import utils.CallUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAllCall#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAllCall extends Fragment {
    private RecyclerView rvCallList;
    private ShimmerFrameLayout shimer_layout;
    private LinearLayout noCallView;

    private MessagesRequest messagesRequest;

    private LinearLayoutManager linearLayoutManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAllCall() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAllCall.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAllCall newInstance(String param1, String param2) {
        MyAllCall fragment = new MyAllCall();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getCallList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_call, container, false);
        rvCallList = view.findViewById(com.cometchat.pro.uikit.R.id.callList_rv);
        noCallView = view.findViewById(com.cometchat.pro.uikit.R.id.no_call_vw);
        shimer_layout = view.findViewById(R.id.shimmer_layout);

        return view;
    }
    public void getCallList() {
        MessagesRequest messagesRequest =new MessagesRequest.MessagesRequestBuilder().setCategory(CometChatConstants.CATEGORY_CALL).setLimit(30).build();
        messagesRequest.fetchPrevious(new CometChat.CallbackListener<List<BaseMessage>>() {
            @Override
            public void onSuccess(List<BaseMessage> baseMessages) {
                Collections.reverse(baseMessages);
                for(int i = baseMessages.size()-1;i>=0;i-=2) {
                    baseMessages.remove(i);
                }

                Log.e("onSuccess tablayput ", baseMessages.size() +"");
                shimer_layout.stopShimmer();
                shimer_layout.setVisibility(View.GONE);
                updateUI(baseMessages);
            }

            @Override
            public void onError(CometChatException e) {
            }
        });
    }



    private void initiateCall(Call var,String callType) {
        CometChat.initiateCall(var, new CometChat.CallbackListener<Call>() {
            @Override
            public void onSuccess(Call call) {
                Log.e("onSuccess: ", call.toString());
                if (call.getReceiverType().equals(CometChatConstants.RECEIVER_TYPE_USER)) {
                    User user;
                    if (((User) call.getCallInitiator()).getUid().equals(CometChat.getLoggedInUser().getUid())) {
                        user = ((User) call.getCallReceiver());
                    } else {
                        user = (User) call.getCallInitiator();
                    }
                    CallUtils.startCallIntent(getContext(), user, callType, true, call.getSessionId());
                } else
                    CallUtils.startGroupCallIntent(getContext(), ((Group) call.getCallReceiver()), callType, true, call.getSessionId());
            }

            @Override
            public void onError(CometChatException e) {
                if (rvCallList != null)
                    Snackbar.make(rvCallList, e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
    private void updateUI(List<BaseMessage> calls) {
        rvCallList.setLayoutManager(new LinearLayoutManager(getContext()));
        CallAdapter callAdapter = new CallAdapter(calls, getContext());
        rvCallList.setAdapter(callAdapter);
    }

}