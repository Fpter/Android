package com.example.myapplication.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.GroupsRequest;
import com.cometchat.pro.core.UsersRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.Group;
import com.cometchat.pro.models.User;
import com.example.myapplication.R;
import com.example.myapplication.adapters.GroupAdapter;
import com.google.android.material.dialog.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.textfield.TextInputLayout;
public class GroupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
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
        getGroupList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        FloatingActionButton addGroup = view.findViewById(R.id.addGroup);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddGroupDialog addGroupDialog = new AddGroupDialog();
                addGroupDialog.show(getParentFragmentManager(), "Add new Group");
            }
        });
        EditText search_user = view.findViewById(R.id.search_user);

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
                GroupsRequest groupsRequest = new GroupsRequest.GroupsRequestBuilder().setSearchKeyWord(keyword).setLimit(30).build();
                groupsRequest.fetchNext(new CometChat.CallbackListener<List<Group>>() {
                    @Override
                    public void onSuccess(List <Group> list) {
                        updateUI(list);
                    }
                    @Override
                    public void onError(CometChatException e) {
                        Log.d("group_list", "Group list fetching failed with exception: " + e.getMessage());
                    }
                });
            }
        });
        return view;

    }
    private void getGroupList() {
        GroupsRequest groupsRequest = new GroupsRequest.GroupsRequestBuilder().joinedOnly(true).setLimit(10).build();
        groupsRequest.fetchNext(new CometChat.CallbackListener<List<Group>>() {
            @Override
            public void onSuccess(List<Group> list) {
                Log.d("group_comet", "Groups list fetched successfully: " + list.size());
                updateUI(list);
            }
            @Override
            public void onError(CometChatException e) {
                Log.d("group_comet", "Groups list fetching failed with exception: " + e.getMessage());
            }
        });
    }

    private void updateUI(List<Group> list) {
        try{
            RecyclerView groupRecyclerView = getView().findViewById(R.id.groupRecycleView);
            groupRecyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
            GroupAdapter groupAdapter = new GroupAdapter(list, getContext());
            groupRecyclerView.setAdapter(groupAdapter);
        }catch (Exception e) {

        }

    }
}