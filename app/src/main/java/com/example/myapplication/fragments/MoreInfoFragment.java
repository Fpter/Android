package com.example.myapplication.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.Avatar;
import com.example.myapplication.R;
import com.example.myapplication.utils.UserUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreInfoFragment extends Fragment {
    private Avatar avatar;
    private TextView username;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoreInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreInfoFragment newInstance(String param1, String param2) {
        MoreInfoFragment fragment = new MoreInfoFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        User user = CometChat.getLoggedInUser();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_info, container, false);
        avatar = view.findViewById(R.id.iv_user);
        username = view.findViewById(R.id.username);
        RelativeLayout avatar_update = view.findViewById(R.id.user_container);
        Log.d("width",avatar_update.getWidth() +" ");


//        Dialog config
        Dialog dialog = new Dialog(getContext());

        // set size and view
        dialog.setContentView(R.layout.update_user);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
        dialog.getWindow().setLayout(width, height);

        //get element
        MaterialButton cancelBtn = dialog.findViewById(R.id.cancelBtn);
        MaterialButton updateBtn = dialog.findViewById(R.id.updateUserBtn);
        Avatar avatar_dialog = dialog.findViewById(R.id.user_avatar);
        avatar_dialog.setAvatar(CometChat.getLoggedInUser().getAvatar());
        TextInputEditText avatar_url_edt = dialog.findViewById(R.id.avatar_url_edt);
        TextInputEditText username_edt = dialog.findViewById(R.id.username_edt);

        //set event for button cancel and update
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cancel btn", "clicked");
                dialog.dismiss();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                String url_avatar = avatar_url_edt.getText().toString();

                String name = username_edt.getText().toString();
                if(name.isEmpty()) {
                    username_edt.setError("You need to enter name");
                    valid = false;
                }
                if (valid) {
                    user.setName(name);
                    username.setText(name);
                    UserUtils.updateUserLogined(name);
                    dialog.dismiss();
                }
            }
        });
        avatar_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_edt.setText(user.getName());
                dialog.show();

            }
        });




        if(user!= null) {
            avatar.setAvatar(user);
            username.setText(user.getName());
        }

        return view;
    }
}