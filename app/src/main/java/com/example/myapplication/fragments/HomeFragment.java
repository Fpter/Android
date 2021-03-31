package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.ConversationsRequest;
import com.cometchat.pro.core.UsersRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.Conversation;
import com.cometchat.pro.models.User;
import com.example.myapplication.NewCallList;
import com.example.myapplication.R;
import com.example.myapplication.adapters.ConversationAdapter;
import com.example.myapplication.adapters.UserAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ImageView backBtn;
    private ShimmerFrameLayout shimmer_layout;
    private FloatingActionButton addConversation;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        getUserList("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        EditText search_user = view.findViewById(R.id.search_user);
        shimmer_layout = view.findViewById(R.id.shimmer_layout);
        addConversation = view.findViewById(R.id.floating_action_button);
        addConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewCallList.class);
                startActivity(intent);
            }
        });
        search_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keyword = search_user.getText().toString();
                getUserList(keyword);
            }
        });
        return view;
    }
    private void getUserList(String key) {

        ConversationsRequest conversationsRequest = new ConversationsRequest.ConversationsRequestBuilder().setConversationType(CometChatConstants.CONVERSATION_TYPE_USER).setLimit(50).build();

        conversationsRequest.fetchNext(new CometChat.CallbackListener<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
//                 Hanlde list of conversations
                Log.d("conversation", conversations.size() + "");
                shimmer_layout.stopShimmer();
                shimmer_layout.setVisibility(View.GONE);

                for(int i = conversations.size()-1;i>=0;i--) {
                    if(!((User)conversations.get(i).getConversationWith()).getName().toLowerCase().contains(key.toLowerCase())) {
                        conversations.remove(i);
                    }
                }

                updateUI(conversations);

            }

            @Override
            public void onError(CometChatException e) {
                // Hanlde failure
            }
        });
    }

    private void updateUI(List<Conversation> conversations) {
        try {
            RecyclerView conversationRecyclerView = getView().findViewById(R.id.homeRecycleView);

            conversationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ConversationAdapter conversationAdapter = new ConversationAdapter(conversations, getContext());
            conversationRecyclerView.setAdapter(conversationAdapter);
        }catch (Exception e) {

        }

    }
}