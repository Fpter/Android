package com.example.myapplication.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cometchat.pro.uikit.Settings.UISettings;
import com.example.myapplication.NewCallList;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import adapter.TabAdapter;
import screen.CometChatUserCallListScreenActivity;
import screen.call.AllCall;
import screen.call.MissedCall;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallFragment extends Fragment {
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;
    private ViewPager viewPager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallFragment newInstance(String param1, String param2) {
        CallFragment fragment = new CallFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.call_screen, container, false);


        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        if (getActivity() != null) {
            tabAdapter = new TabAdapter(getActivity().getSupportFragmentManager());
            tabAdapter.addFragment(new MyAllCall(), getContext().getResources().getString(com.cometchat.pro.uikit.R.string.all));
            tabAdapter.addFragment(new MyMissedCall(), getContext().getResources().getString(com.cometchat.pro.uikit.R.string.missed));
            viewPager.setAdapter(tabAdapter);
        }
        tabLayout.setupWithViewPager(viewPager);
        if (UISettings.getColor()!=null) {
            Drawable wrappedDrawable = DrawableCompat.wrap(getResources().
                    getDrawable(com.cometchat.pro.uikit.R.drawable.tab_layout_background_active));
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(UISettings.getColor()));
            tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).view.setBackground(wrappedDrawable);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor(UISettings.getColor()));
        } else {
            tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).
                    view.setBackgroundColor(getResources().getColor(com.cometchat.pro.uikit.R.color.colorPrimary));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(com.cometchat.pro.uikit.R.color.colorPrimary));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (UISettings.getColor()!=null) {
                    Drawable wrappedDrawable = DrawableCompat.wrap(getResources().
                            getDrawable(com.cometchat.pro.uikit.R.drawable.tab_layout_background_active));
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor(UISettings.getColor()));
                    tab.view.setBackground(wrappedDrawable);
                }
                else
                    tab.view.setBackgroundColor(getResources().getColor(com.cometchat.pro.uikit.R.color.colorPrimary));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}