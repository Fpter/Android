package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class Dashboard extends AppCompatActivity {
    private Animation topAnim, bottomAnim;
    private ImageView image;
    private TextView slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
//        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        try {
            Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        }catch (Exception e) {}
        setContentView(R.layout.activity_dashboard);

        // Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        image = findViewById(R.id.imageDashboard);
        slogan = findViewById(R.id.slogan);
        image.setAnimation(topAnim);
        slogan.setAnimation(bottomAnim);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Dashboard.this, MainActivity.class);

            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(image, "logo_image");
            pairs[1] = new Pair<View, String>(slogan, "logo_text");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Dashboard.this, pairs);
            startActivity(intent, options.toBundle());

        }, 2000);
    }
}