package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.cometchat.pro.models.User;
import com.example.myapplication.R;
import com.example.myapplication.adapters.CallAdapter;
import com.example.myapplication.adapters.MissedCallAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyMissedCall#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyMissedCall extends Fragment {
    private MessagesRequest messagesRequest;
    private RecyclerView rvCallList;
    private ShimmerFrameLayout shimer_layout;
    private LinearLayout noCallView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String loggedInUid;
    public MyMissedCall() {
        // Required empty public constructor
        if(!CometChat.isInitialized())
            loggedInUid = CometChat.getLoggedInUser().getUid();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyMissedCall.
     */
    // TODO: Rename and change types and number of parameters
    public static MyMissedCall newInstance(String param1, String param2) {
        MyMissedCall fragment = new MyMissedCall();
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
        View view = inflater.inflate(R.layout.fragment_missed_call, container, false);
        rvCallList = view.findViewById(com.cometchat.pro.uikit.R.id.callList_rv);
        noCallView = view.findViewById(com.cometchat.pro.uikit.R.id.no_call_vw);
        shimer_layout = view.findViewById(R.id.shimmer_layout);

        return view;
    }
    private void getCallList(){
        if(messagesRequest == null) {
            messagesRequest = new MessagesRequest.MessagesRequestBuilder().setCategories(Arrays.asList(CometChatConstants.CATEGORY_CALL)).build();
        }
        messagesRequest.fetchPrevious(new CometChat.CallbackListener<List<BaseMessage>>() {
            @Override
            public void onSuccess(List<BaseMessage> baseMessages) {
                Collections.reverse(baseMessages);
                baseMessages = filerMessedCall(baseMessages);
                updateUI(baseMessages);
            }

            @Override
            public void onError(CometChatException e) {

            }
        });

    }

    private void updateUI(List<BaseMessage> baseMessages) {
        rvCallList.setLayoutManager(new LinearLayoutManager(getContext()));
        MissedCallAdapter missedCallAdapter = new MissedCallAdapter(baseMessages, getContext());
        rvCallList.setAdapter(missedCallAdapter);
    }

    private List<BaseMessage> filerMessedCall(List<BaseMessage> baseMessages) {
        List<BaseMessage> filteredList = new ArrayList<>();
        for(BaseMessage baseMessage: baseMessages) {
            Call call = (Call) baseMessage;
            if(!((User) call.getCallInitiator()).getUid().equals(loggedInUid)){
                if(call.getCallStatus().equals(CometChatConstants.CALL_STATUS_CANCELLED)
                || call.getCallStatus().equals(CometChatConstants.CALL_STATUS_UNANSWERED)) {
                    filteredList.add(baseMessage);
                }
            }
        }
        return filteredList;
    }
}